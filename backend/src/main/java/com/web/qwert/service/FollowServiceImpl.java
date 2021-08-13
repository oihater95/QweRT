package com.web.qwert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.FollowDao;
import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.comment.CommentDto;
import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.follow.FollowDto;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;

@Service
public class FollowServiceImpl {

	@Autowired
	FollowDao followDao;

	public void updateFollow(User fromUser, User toUser) {

		Optional<Follow> followOpt = followDao.findByFromUserAndToUser(fromUser, toUser);
		if (followOpt.isPresent()) { // 이미 팔로우 했다면
			followDao.delete(followOpt.get()); // 팔로우 취소

		} else { // 안했다면 팔로우 하기
			Follow follow = new Follow();
			follow.setFromUser(fromUser);
			follow.setToUser(toUser);
			followDao.save(follow);
		}

	}

	public Optional<Follow> getFollow(User fromUser, User toUser) {
		return followDao.findByFromUserAndToUser(fromUser, toUser);
	}
	
	// 비로그인  상태에서 프로필 유저의 팔로워 리스트 조회
	public List<UserDto> getFollowers(User profileUser, int page, int size) {
		
		// 프로필 유저의 최신 순 팔로워를 size만큼 페이징해서 가져움
		Pageable pageable = PageRequest.of(page, size, Sort.by("followId").descending());
		List<Follow> follows = followDao.findByToUser(profileUser, pageable);


		List<UserDto> results = new ArrayList<>();
		for (Follow follow : follows) {
			
			// 팔로워 유저 정보 저장
			UserDto follower = new UserDto(); 
			BeanUtils.copyProperties(follow.getFromUser(), follower);
			results.add(follower);
		}	
		return results;
	}
	
	// 로그인 상태에서 프로필 유저의 팔로워 리스트 및 각 팔로워와 로그인 유저간의 팔로우 정보 조회
	public List<FollowDto> getFollowersAndFlags(User loginUser, User profileUser, int page, int size) {
		
		// 프로필 유저의 최신 순 팔로워 정보를 size만큼 페이징해서 가져움
		Pageable pageable = PageRequest.of(page, size, Sort.by("followId").descending());
		List<Follow> follows = followDao.findByToUser(profileUser, pageable);

		// FollowDto에 팔로워 유저 정보와 로그인 유저와의 팔로우 여부 저장
		List<FollowDto> results = new ArrayList<FollowDto>();
		for (Follow follow : follows) {
			FollowDto followDto = new FollowDto();
			
			// 팔로워 유저 정보 저장
			User follower = follow.getFromUser();
			UserDto followerDto = new UserDto(); 
			BeanUtils.copyProperties(follower, followerDto);
			followDto.setUser(followerDto);
			
			// 로그인 유저와의 팔로우 여부 저장
			Optional<Follow> followOpt = getFollow(loginUser, follower);
			if(followOpt.isPresent()) followDto.setFollowFlag(true);
			
			results.add(followDto);
		}
		
		return results;
	}
}
