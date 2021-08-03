package com.web.qwert.service;

import java.util.Optional;

import com.web.qwert.model.user.User;

public interface UserService {
	
	public Optional<User> getUser(int user_id);
	public Optional<User> getUser(String email, String nickname);
	public Optional<User> getUserByEmail(String email);
	public Optional<User> getUserByNickname(String nickname);
	public Optional<User> getUserByEmailAndPassword(String email, String password);
	
	public User setUser(User user);
	public void deleteUser(int user_id);

}