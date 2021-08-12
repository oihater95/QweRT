package com.web.qwert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.service.FollowServiceImpl;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.PostingService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/follow")
public class FollowController {
	
	@Autowired
	FollowServiceImpl followService;
	
	@Autowired
	JwtService jwtService;

	@Autowired
	UserService userService;
	
	@PutMapping("{fromUserId}/{toUserId}")
	@ApiOperation("팔로우 토글")
	public Object followToggle (@PathVariable int fromUserId, @PathVariable int toUserId, @RequestHeader String token) {
		Optional<User> fromUserOpt = userService.getUser(fromUserId);
		Optional<User> toUserOpt = userService.getUser(toUserId);
		
		   if(!fromUserOpt.isPresent() || !toUserOpt.isPresent()) 
			   return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원

		   try {
			   if(fromUserId == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
				   
				   followService.updateFollow(fromUserOpt.get(), toUserOpt.get());
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
