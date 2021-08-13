package com.web.qwert.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.Feed.Feed;
import com.web.qwert.model.user.User;

@Repository
public interface FeedDao extends JpaRepository<Feed, Integer>{

	List<Feed> findByUser(User user, Pageable pageable);
}
