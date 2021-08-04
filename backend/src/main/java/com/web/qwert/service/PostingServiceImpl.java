package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CategoryDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.dao.UserDao;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;

@Service
public class PostingServiceImpl implements PostingService {
	
	@Autowired
    UserDao userDao;
	
	@Autowired
	CategoryDao CategoryDao;
	
	@Autowired
	PostingDao postingDao;
	
	@Override
	public boolean createPosting(UploadRequest request) {
		
		int user_id = request.getUserId();
        Optional<User> userOpt = userDao.findById(user_id); //id로 user 찾기
        
        if (userOpt.isPresent()) { // 회원이면 posting 생성
            Posting posting = new Posting();
            posting.setUser(userOpt.get());
            posting.setTitle(request.getPostingTitle());
            posting.setContent(request.getPostingContent());
            posting.setPostingImg(request.getPostingImage());
            posting.setCategory(CategoryDao.getOne(request.getCategoryId()));
            postingDao.save(posting);
            return true;
            
        } else { // 비회원
           return false;
        }

	}

	@Override
	public List<Posting> getPostingsByUser(User user, int page) {
		return user.getPostings();
	}


}
