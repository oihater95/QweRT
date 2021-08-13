package com.web.qwert.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.user.User;

@Repository
public interface FollowDao extends JpaRepository<Follow, Integer>{
	
	Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
}
