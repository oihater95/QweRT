package com.web.qwert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CommentDao;
import com.web.qwert.dao.FeedDao;
import com.web.qwert.model.Feed.Feed;
import com.web.qwert.model.Feed.FeedDto;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	FeedDao feedDao;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CurationService curationService;
	
	// 유저에게 피드 추가
	@Override
	public void addFeed(User user, Posting posting) {
		Feed feed = new Feed();
		feed.setUser(user);
		feed.setPosting(posting);
		feedDao.save(feed);
	}
	
	// 팔로워들에게 피드 추가
	@Override
	public void addFeedToFollwers (List<User> followers, Posting posting) {
		List<Feed> feeds = new ArrayList<Feed>();
		
		for(User follower : followers) {
			Feed feed = new Feed();
			feed.setUser(follower);
			feed.setPosting(posting);
			
			feeds.add(feed);
		}
		feedDao.saveAll(feeds);
	}
	
	// 유저의 피드 목록 불러오기
	@Override
	public List<FeedDto> getFeedByUser(User user, int page, int size) {
		List<FeedDto> feedDtos = new ArrayList<FeedDto>();
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		List<Feed> feeds = feedDao.findByUser(user, pageable);
		
		for(Feed feed : feeds) {
			// 게시물 정보 저장
			FeedDto feedDto = new FeedDto();
			Posting posting = feed.getPosting();
			BeanUtils.copyProperties(posting, feedDto);
			feedDto.setCommentCnt(commentService.getTotalCnt(posting));
			feedDto.setCuratedCnt(curationService.getCuratedCnt(posting));
			
			// 게시자 정보 저장
			UserDto uploader = new UserDto();
			BeanUtils.copyProperties(posting.getUser(), uploader);
			feedDto.setUser(uploader);
			
			feedDtos.add(feedDto);
		}
		
		return feedDtos;
	}
}
