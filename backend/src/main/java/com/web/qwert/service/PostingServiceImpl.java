package com.web.qwert.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CategoryDao;
import com.web.qwert.dao.CommentDao;
import com.web.qwert.dao.CurationHasPostingDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.dao.UserDao;
import com.web.qwert.model.category.Category;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.PostingDto;
import com.web.qwert.model.posting.UpdateRequest;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;

@Service
public class PostingServiceImpl implements PostingService {
	
	@Autowired
    UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	PostingDao postingDao;
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	CurationServiceImpl curationService;
	
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
            posting.setCategory(categoryDao.getOne(request.getCategoryId()));
            postingDao.save(posting);
            return true;
            
        } else { // 비회원
           return false;
        }

	}

	@Override
	public List<Posting> getPostingsByUser(User user, int page, int size) {
		//page말고 파라미터가 있는 경우 pageable만 인식됨
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return postingDao.findByUser(user, pageable);
//		return postings.stream().sorted((a,b) -> (b.getPostingId() - a.getPostingId()))
//				.skip(page * 3)
//				.limit(3)
//				.collect(Collectors.toList());
	}
	
	@Override
	public List<Posting> getFavoritePostings(User user, int page, int size) {
		// 최신순으로 유저의 좋아요 리스트 size만큼 읽어옴
		List<Like> likes = likeService.getLikesByUser(user, page, size); 
		List<Posting> postings = new ArrayList<Posting>();
		
		// 좋아요한 게시물 리스트를 구함
		for(Like like : likes) {
			Posting posting = like.getPosting();
			postings.add(posting);
			
		}
		return postings;
	}
	
	@Override
	public List<Posting> getNewPostings(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createDate").descending());
		return postingDao.findAll(pageRequest).getContent();
	}
	
	@Override
	public List<Posting> getPopularPostings(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("likeCnt").descending());
		return postingDao.findAll(pageRequest).getContent();
	}
	
	@Override
	public Optional<Posting> getPosting(int postingId) {
		return postingDao.findById(postingId);
	}

	@Override
	public PostingDto getPostingDetail(Posting posting) {
		PostingDto postingDto = new PostingDto();
		BeanUtils.copyProperties(posting, postingDto);
		
		User uploader = posting.getUser();
		postingDto.setCategoryId(posting.getCategory().getCategoryId());
		postingDto.setUserId(uploader.getUserId());
		postingDto.setNickname(uploader.getNickname());
		postingDto.setCommentCnt(commentDao.countByPostingAndDocentFlag(posting, false));
		postingDto.setDocentCnt(commentDao.countByPostingAndDocentFlag(posting, true));
		postingDto.setCuratedCnt(curationService.getCuratedCnt(posting));
		return postingDto;
	}

	@Override
	public void removePosting(Posting posting) {
		postingDao.delete(posting);
	}

	@Override
	public void updatePosting(Posting posting, UpdateRequest request) {
		posting.setTitle(request.getPostingTitle());
		posting.setContent(request.getPostingContent());
		Category category = categoryDao.getOne(request.getCategoryId());
		posting.setCategory(category);
		posting.setUpdateDate(LocalDateTime.now());
		postingDao.save(posting);
	}




}
