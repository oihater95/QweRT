package com.web.qwert.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.FollowDao;
import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.user.User;

@Service
public class FollowServiceImpl {

	@Autowired
	FollowDao followDao;
	
	public void updateFollow(User fromUser, User toUser) {
		
		Optional<Follow> followOpt = followDao.findByFromUserAndToUser(fromUser, toUser);
		if(followOpt.isPresent()) { // 이미 팔로우 했다면 
			followDao.delete(followOpt.get()); // 팔로우 취소
	
		} else { // 안했다면 팔로우 하기
			Follow follow = new Follow();
			follow.setFromUser(fromUser);
			follow.setToUser(toUser);
			followDao.save(follow);
		}

	}
}
