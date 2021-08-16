package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.comment.CommentDto;
import com.web.qwert.model.comment.CommentRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

public interface CommentService {

	void addComment(User user, Posting posting, CommentRequest request);

	Optional<Comment> getComment(int commentId);

	void deleteComment(Comment comment);

	void updateComment(Comment comment, String content);

	List<CommentDto> getComments(Posting posting, int page, int size);

	List<CommentDto> getDocentComments(Posting posting, int page, int size);
	
	int getTotalCnt (Posting posting);

}