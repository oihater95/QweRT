package com.web.qwert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.user.User;

@Repository
public interface FollowDao extends JpaRepository<Follow, Integer>{
	
	Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);
	
	List<Follow> findByToUser(User toUser, Pageable pageable);
	
	List<Follow> findByFromUser(User fromUser, Pageable pageable);
	
	int countByToUser(User toUser);
	
	int countByFromUser(User fromUser);
}
