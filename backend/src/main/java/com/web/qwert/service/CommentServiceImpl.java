package com.web.qwert.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CommentDao;
import com.web.qwert.dao.PostingDao;
import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.comment.CommentDto;
import com.web.qwert.model.comment.CommentRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;

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
	
	public Optional<Comment> getComment (int commentId) {
		return commentDao.findById(commentId);
	}
	
	public void deleteComment (Comment comment) {
		commentDao.delete(comment);
	}
	
	public void updateComment (Comment comment, String content) {
		comment.setContent(content);
		comment.setUpdateDate(LocalDateTime.now());
		commentDao.save(comment);
	}
	
	public List<CommentDto> getComments (Posting posting, int page, int size) {
		
		// 게시물의 최신 순 댓글들을 size만큼 페이징해서 가져움
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending());
		List<Comment> comments = commentDao.findByPostingAndDocentFlag(posting, false, pageable);
		
		// 페이지의 모든 댓글 정보에 작성자 정보 추가
		List<CommentDto> results = new ArrayList<CommentDto>();
		for(Comment comment : comments) {
			CommentDto commentDto = new CommentDto(); // 댓글 정보 저장
			BeanUtils.copyProperties(comment, commentDto);
			
			UserDto user = new UserDto(); // 댓글 작성자 정보 저장
			BeanUtils.copyProperties(comment.getUser(), user);
			commentDto.setUser(user);
			results.add(commentDto);	
		}
		return results;
	}
	
	public List<CommentDto> getDocentComments (Posting posting, int page, int size) {
		
		// 게시물의 오래된 순 도슨트 댓글들을 size만큼 페이징해서 가져움
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending());
		List<Comment> comments = commentDao.findByPostingAndDocentFlag(posting, true, pageable);
		
		// 페이지의 모든 댓글 정보에 작성자 정보 추가
		List<CommentDto> results = new ArrayList<CommentDto>();
		for(Comment comment : comments) {
			CommentDto commentDto = new CommentDto(); // 댓글 정보 저장
			BeanUtils.copyProperties(comment, commentDto);
			
			UserDto user = new UserDto(); // 댓글 작성자 정보 저장
			BeanUtils.copyProperties(comment.getUser(), user);
			commentDto.setUser(user);
			results.add(commentDto);	
		}
		return results;
	}
}
