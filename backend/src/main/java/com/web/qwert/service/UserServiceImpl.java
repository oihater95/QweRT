package com.web.qwert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CurationDao;
import com.web.qwert.dao.FollowDao;
import com.web.qwert.dao.LikeDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.dao.UserDao;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.ChangeInfoRequest;
import com.web.qwert.model.user.ProfileDto;
import com.web.qwert.model.user.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    UserDao userDao;
	
	@Autowired
    PostingDao postingDao;
	
	@Autowired
	LikeDao likeDao;
	
	@Autowired
	FollowDao followDao;
	
	@Autowired
	CurationService curationService;
	
	@Override
	public Optional<User> getUser(int user_id) {
		return userDao.findById(user_id);
		//getOne(user_id);
	}
	
	@Override
	public  Optional<User> getUser (String email, String nickname) {
		return userDao.findUserByEmailOrNickname(email, nickname);
	}
	
	@Override
	public User setUser (User user) {
		return userDao.save(user);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public Optional<User> getUserByNickname(String nickname) {
		return userDao.findUserByNickname(nickname);
	}

	@Override
	public Optional<User> getUserByEmailAndPassword(String email, String password) {
		return userDao.findUserByEmailAndPassword(email, password);
	}

	@Override
	public void deleteUser(int user_id) {
		userDao.deleteById(user_id);
	}

	@Override
	public Posting[] updateUserInfo(User user, ChangeInfoRequest request) {
	   // 유저 정보 변경
	   user.setIntroduction(request.getIntroduction());
	   user.setNickname(request.getNickname());
	   user.setProfileImg(request.getProfileImage());
	   userDao.save(user);
	   
	   // 유저의 기존 대표작 정보 초기화
	   List<Posting> currentMps = postingDao.findPostingByUserAndMasterpieceFlag(user, true);
	   for (Posting mp : currentMps) {
		   mp.setMasterpieceFlag(false);
		   postingDao.save(mp);
	   }
	   
	   // 게시글 정보 변경
	   int[] posting_ids = request.getMasterpieceIds();
	   Posting[] masterpieces = new Posting[posting_ids.length]; // 대표작 배열
	   List<Posting> postings = new ArrayList<Posting>(); // 대표작 선정된 게시글 리스트
	   
	   for (int i = 0; i < posting_ids.length; i++) {
		   Posting posting = postingDao.findById(posting_ids[i]).get(); // getOne 사용시 hibernateLazyInitializer 추가됨
		   posting.setMasterpieceFlag(true);
		   postings.add(posting);
		   masterpieces[i] = posting;
		   
	   }

	   postingDao.saveAll(postings); // 선정된 게시글 대표작으로 수정
	   
	   return masterpieces; // 대표작 배열 리턴
	}

	@Override
	public ProfileDto getProfile(User user) {
		
		ProfileDto profileDto = new ProfileDto();
		BeanUtils.copyProperties(user, profileDto);
		
		profileDto.setLikedCnt(likeDao.countByUploaderId(user.getUserId()));
		profileDto.setPostingCnt(postingDao.countByUser(user));
		
		List<Posting> currentMps = postingDao.findPostingByUserAndMasterpieceFlag(user, true);
		Posting[] masterpieces = new Posting[currentMps.size()];
		for (int i = 0; i < currentMps.size(); i++) {
			masterpieces[i] = currentMps.get(i);
		}
		profileDto.setMasterpieces(masterpieces);
		
		profileDto.setCuratedCnt(curationService.getTotalCuratedCnt(user));
		profileDto.setFollowerCnt(followDao.countByToUser(user));
		profileDto.setFollowingCnt(followDao.countByFromUser(user));
		
		return profileDto;
	}

	@Override
	public List<User> searchUserByNickname(String nickname, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return userDao.findByNicknameContaining(nickname, pageable);
	}

}
