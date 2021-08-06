package com.web.qwert.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Repository
public interface LikeDao extends JpaRepository<Like, Integer> {
	Optional<Like> findLikeByUserAndPosting(User user, Posting posting);
	
}
