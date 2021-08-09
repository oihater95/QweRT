package com.web.qwert.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.comment.Comment;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer>{

}
