package com.web.qwert.service;

import java.util.List;

import com.web.qwert.model.Feed.FeedDto;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

public interface FeedService {

	// 유저에게 피드 추가
	void addFeed(User user, Posting posting);

	// 팔로워들에게 피드 추가
	void addFeedToFollwers(List<User> followers, Posting posting);

	// 유저의 피드 목록 불러오기
	List<FeedDto> getFeedByUser(User user, int page, int size);

}