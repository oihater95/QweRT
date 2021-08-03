package com.web.qwert.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.UserDao;
import com.web.qwert.model.user.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    UserDao userDao;
	
	@Override
	public Optional<User> getUser(int user_id) {
		return userDao.findById(user_id);
		//getOne(user_id);
	}
	
	@Override
	public  Optional<User> getUser (String email, String nickname) {
		return userDao.findUserByEmailOrNickname(email, nickname);
	}
	
	@Override
	public User setUser (User user) {
		return userDao.save(user);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public Optional<User> getUserByNickname(String nickname) {
		return userDao.findUserByNickname(nickname);
	}

	@Override
	public Optional<User> getUserByEmailAndPassword(String email, String password) {
		return userDao.findUserByEmailAndPassword(email, password);
	}

	@Override
	public void deleteUser(int user_id) {
		userDao.deleteById(user_id);
	}



	
}
