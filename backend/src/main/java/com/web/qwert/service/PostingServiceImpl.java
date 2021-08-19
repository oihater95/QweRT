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
import com.web.qwert.dao.PostingDao;
import com.web.qwert.dao.UserDao;
import com.web.qwert.model.category.Category;
import com.web.qwert.model.follow.Follow;
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
	CurationService curationService;
	
	@Autowired
	FeedService feedService;
	
	@Override
	public void createPosting(UploadRequest request) {
		
		int user_id = request.getUserId();
    	User user = userDao.findById(user_id).get();
    	
        Posting posting = new Posting();
        posting.setUser(user);
        posting.setTitle(request.getPostingTitle());
        posting.setContent(request.getPostingContent());
        posting.setPostingImg(request.getPostingImage());
        posting.setCategory(categoryDao.getOne(request.getCategoryId()));
        postingDao.save(posting);
        
        feedService.addFeed(user, posting); // 내 피드 추가
        
        // 팔로워 리스트 불러오기
        List<Follow> follows = user.getFollower();
        List<User> followers = new ArrayList<User>();
        for(Follow follow : follows) {
        	followers.add(follow.getFromUser());
        }
        
        feedService.addFeedToFollwers(followers, posting); // 팔로워들에게 피드 추가

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
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("likeCnt").descending().and(Sort.by("createDate").descending()));
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

	@Override
	public List<Posting> searchNewByCategory(Category category, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return postingDao.findByCategory(category, pageable);

	}
	
	@Override
	public List<Posting> searchPopularByCategory(Category category, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("likeCnt").descending());
		return postingDao.findByCategory(category, pageable);

	}
	
	@Override
	public List<Posting> searchNewByTerm(String term, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return postingDao.findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(term, term, pageable);

	}
	
	@Override
	public List<Posting> searchPopularByTerm(String term, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("likeCnt").descending().and(Sort.by("createDate").descending()));
		return postingDao.findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(term, term, pageable);

	}


}
