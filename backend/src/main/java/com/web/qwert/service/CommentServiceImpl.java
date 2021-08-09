package com.web.qwert.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CommentDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.comment.CommentRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Service
public class CommentServiceImpl {
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	PostingDao postingDao;
	
	public void addComment (User user, Posting posting, CommentRequest request) {
		
		Comment comment = new Comment();
		comment.setUser(user);
		comment.setPosting(posting);
		comment.setContent(request.getContent());
		comment.setDocentFlag(request.isDocentFlag());
		commentDao.save(comment);
	}
}
