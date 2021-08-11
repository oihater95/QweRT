package com.web.qwert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.LikeService;
import com.web.qwert.service.PostingService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/like")
public class LikeController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostingService postingService;
	
	@Autowired
	LikeService likeService;
	
	
	@GetMapping("{postingId}/{userId}")
	@ApiOperation("좋아요 여부 확인")
	public Object likeCheck (@PathVariable int postingId, @PathVariable int userId, @RequestHeader String token) {
		Optional<User> userOpt = userService.getUser(userId);
		Optional<Posting> postingOpt = postingService.getPosting(postingId);
		
		   if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		   if(!postingOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시글
		   try {
			   if(userId == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
				   
				   Optional<Like> likeOpt = likeService.getLike(userOpt.get(), postingOpt.get());
				   if(!likeOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 좋아요 안함
				   return new ResponseEntity<>(HttpStatus.OK); // 좋아요 함
				   
			   } else { // 인증 실패
				   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			   }
		   } catch (Exception e) { // 유효하지 않은 토큰
			   e.printStackTrace();
			   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		   }
	}
	
	@PutMapping("{postingId}/{userId}")
	@ApiOperation("좋아요 토글")
	public Object likeToggle (@PathVariable int postingId, @PathVariable int userId, @RequestHeader String token) {
		Optional<User> userOpt = userService.getUser(userId);
		Optional<Posting> postingOpt = postingService.getPosting(postingId);
		
		   if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		   if(!postingOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시글
		   try {
			   if(userId == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
				   
				   likeService.updateLike(userOpt.get(), postingOpt.get());
				   return new ResponseEntity<>(HttpStatus.OK);
				   
			   } else { // 인증 실패
				   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			   }
		   } catch (Exception e) { // 유효하지 않은 토큰
			   e.printStackTrace();
			   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		   }
	}
}
