package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.ChangeInfoRequest;
import com.web.qwert.model.user.ProfileDto;
import com.web.qwert.model.user.User;

public interface UserService {
	
	public Optional<User> getUser(int user_id);
	public Optional<User> getUser(String email, String nickname);
	public Optional<User> getUserByEmail(String email);
	public Optional<User> getUserByNickname(String nickname);
	public Optional<User> getUserByEmailAndPassword(String email, String password);
	
	public User setUser(User user);
	public void deleteUser(int user_id);
	
	public Posting[] updateUserInfo(User user, ChangeInfoRequest userInfo);
	
	public ProfileDto getProfile(User user);
	
	public List<ProfileDto> searchNewUserByNickname(String nickname, int page, int size);
	
	public List<ProfileDto> searchPopularUserByNickname(String nickname, int page, int size);
}