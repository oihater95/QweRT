package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        	System.out.println(userOpt.get().getPostings().size() + "개의 포스팅");
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
	public List<Posting> getPostingsByUser(User user, int page, int size) {
		//PageRequest 사용할 시, Page<>로 리턴 후 getContent()
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return postingDao.findByUser(user, pageable);
//		return postings.stream().sorted((a,b) -> (b.getPostingId() - a.getPostingId()))
//				.skip(page * 3)
//				.limit(3)
//				.collect(Collectors.toList());
	}

	@Override
	public List<Posting> getNewPostings(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createDate").descending());
		
		return postingDao.findAll(pageRequest).getContent();
	}


}
