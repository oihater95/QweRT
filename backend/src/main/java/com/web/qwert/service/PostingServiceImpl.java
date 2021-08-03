package com.web.qwert.service;

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
		
		int user_id = request.getUser_id();
        Optional<User> userOpt = userDao.findById(user_id); //id로 user 찾기
        
        if (userOpt.isPresent()) { // 회원이면 posting 생성
            Posting posting = new Posting();
            posting.setUser(userOpt.get());
            posting.setTitle(request.getPosting_title());
            posting.setContent(request.getPosting_content());
            posting.setPosting_img(request.getPosting_image());
            posting.setCategory(CategoryDao.getOne(request.getCategory_id()));
            postingDao.save(posting);
            return true;
            
        } else { // 비회원
           return false;
        }

	}

}
