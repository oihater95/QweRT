package com.web.qwert.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByPostingAndDocentFlag(Posting posting, boolean docentFlag, Pageable pageable);
	
	int countByPostingAndDocentFlag(Posting posting, boolean docentFlag);
	
	int countByPosting(Posting posting);
}
