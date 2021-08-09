package com.web.qwert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.comment.CommentRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.UpdateRequest;
import com.web.qwert.model.user.User;
import com.web.qwert.service.CommentServiceImpl;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.PostingService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	JwtService jwtService;

	@Autowired
	PostingService postingService;

	@Autowired
	UserService userService;
	
	@Autowired
	CommentServiceImpl commentService;

	@PostMapping("{postingId}")
	@ApiOperation("댓글 달기")
	public Object WriteComment(@PathVariable int postingId, @RequestHeader String token,
			@RequestBody CommentRequest request) {
		Optional<User> userOpt = userService.getUser(request.getUserId());
		Optional<Posting> postingOpt = postingService.getPosting(postingId);

		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		if (!postingOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시글
		
		try {
			   if(request.getUserId() == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 				
				   commentService.addComment(userOpt.get(), postingOpt.get(), request);
				   return new ResponseEntity<>(HttpStatus.OK);
				   
			   } else { // 권한 없음
				   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			   }
		   } catch (Exception e) { // 유효하지 않은 토큰
			   e.printStackTrace();
			   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		   }
	}
	
	@DeleteMapping("{commentId}")
	@ApiOperation("댓글 삭제")
	public Object deleteComment(@PathVariable int commentId, @RequestHeader String token) {
		Optional<Comment> commentOpt = commentService.getComment(commentId);
		if (!commentOpt.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 없는 댓글
		
		Comment comment = commentOpt.get(); 
		int userId = comment.getUser().getUserId();

		try {
			if (userId == jwtService.getUserId(token)) { // 댓글 작성자와 토큰 발급한 유저가 같다면
				commentService.deleteComment(comment);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 권한 없음
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 유효하지 않은 토큰
		}

	}
	
	@PutMapping("{commentId}")
	@ApiOperation("댓글 수정")
	public Object updatePosting(@PathVariable int commentId, @RequestHeader String token,
			@RequestBody String content) {
		
		Optional<Comment> commentOpt = commentService.getComment(commentId);
		if (!commentOpt.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 없는 댓글
		
		Comment comment = commentOpt.get(); 
		int userId = comment.getUser().getUserId();
				
		try {
			if (userId == jwtService.getUserId(token)) { // 게시물 작성자와 토큰 발급한 유저가 같다면
				commentService.updateComment(comment, content);
				return new ResponseEntity<>(HttpStatus.OK);
			} else { 
				return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 권한 없음
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 유효하지 않은 토큰
		}

	}
}
