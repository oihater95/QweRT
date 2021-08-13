package com.web.qwert.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.FeedDao;
import com.web.qwert.model.Feed.Feed;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Service
public class FeedServiceImpl {

	@Autowired
	FeedDao feedDao;
	
	// 유저에게 피드 추가
	public void addFeed(User user, Posting posting) {
		Feed feed = new Feed();
		feed.setUser(user);
		feed.setPosting(posting);
		feedDao.save(feed);
	}
	
	// 팔로워들에게 피드 추가
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
	public List<Feed> getFeedByUser(User user, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		return feedDao.findByUser(user, pageable);
	}
}
