package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.follow.FollowDto;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;

public interface FollowService {

	void updateFollow(User fromUser, User toUser);

	Optional<Follow> getFollow(User fromUser, User toUser);

	// 비로그인 상태에서 프로필 유저의 팔로워 리스트 조회
	List<UserDto> getFollowers(User profileUser, int page, int size);

	// 로그인 상태에서 프로필 유저의 팔로워 리스트 및 각 팔로워와 로그인 유저간의 팔로우 정보 조회
	List<FollowDto> getFollowersAndFlags(User loginUser, User profileUser, int page, int size);

	// 비로그인 상태에서 프로필 유저의 팔로잉 리스트 조회
	List<UserDto> getFollowings(User profileUser, int page, int size);

	// 로그인 상태에서 프로필 유저의 팔로핑 리스트 및 각 팔로잉과 로그인 유저간의 팔로우 정보 조회
	List<FollowDto> getFollowingsAndFlags(User loginUser, User profileUser, int page, int size);

}