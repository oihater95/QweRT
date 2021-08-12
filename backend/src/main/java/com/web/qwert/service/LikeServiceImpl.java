package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.LikeDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeDao likeDao;
	
	@Autowired
	PostingDao postingDao;
	
	
	@Override
	public Optional<Like> getLike(User user, Posting posting) {
		return likeDao.findLikeByUserAndPosting(user, posting);
	}

	@Override
	@Transactional
	public void updateLike(User user, Posting posting) {
		Optional<Like> likeOpt = likeDao.findLikeByUserAndPosting(user, posting);
		
		if(likeOpt.isPresent()) { // 좋아요가 있다면 
			likeDao.delete(likeOpt.get()); // 삭제
			posting.setLikeCnt( posting.getLikeCnt() - 1);
			postingDao.save(posting);
		
		} else { // 좋아요 없다면
			Like like = new Like();
			like.setUser(user);
			like.setPosting(posting);
			User uploader = posting.getUser();
			like.setUploaderId(uploader.getUserId());
			likeDao.save(like);
			
			posting.setLikeCnt( posting.getLikeCnt() + 1);
			postingDao.save(posting);
		}

	}

	@Override
	public List<Like> getLikesByUser(User user, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("likeId").descending());
		return likeDao.findLikeByUser(user, pageable);
	}

	
}
