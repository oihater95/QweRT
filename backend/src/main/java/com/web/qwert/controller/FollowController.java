package com.web.qwert.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.follow.FollowDto;
import com.web.qwert.model.user.User;
import com.web.qwert.service.FollowServiceImpl;
import com.web.qwert.service.JwtService;
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
	
	@GetMapping("{fromUserId}/{toUserId}")
	@ApiOperation("팔로우 여부 확인")
	public Object followCheck (@PathVariable int fromUserId, @PathVariable int toUserId, @RequestHeader String token) {
		Optional<User> fromUserOpt = userService.getUser(fromUserId);
		Optional<User> toUserOpt = userService.getUser(toUserId);
		
	   if(!fromUserOpt.isPresent() || !toUserOpt.isPresent()) 
		   return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
	      
	   try {
		   if(fromUserId == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
			   
			   Optional<Follow> followOpt = followService.getFollow(fromUserOpt.get(), toUserOpt.get()); 
			   if(!followOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 팔로우 안함
			   return new ResponseEntity<>(HttpStatus.OK); // 팔로우 함
			   
		   } else { // 인증 실패
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
	   } catch (Exception e) { // 유효하지 않은 토큰
		   e.printStackTrace();
		   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	   }
	}
	
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
	
	@GetMapping("to/{profileId}")
	@ApiOperation("팔로워 목록 조회(비로그인)")
	public Object getFollowers (@PathVariable int profileId, @RequestParam int page, @RequestParam int size) {
		
		Optional<User> userOpt = userService.getUser(profileId);
		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		
		
		return new ResponseEntity<>(followService.getFollowers(userOpt.get(), page, size), HttpStatus.OK);
	}
	
	// 프로필 유저의 팔로워들의 정보와 로그인 한 유저와의 팔로우 여부를 리턴
	@GetMapping("to/{userId}/{profileId}")
	@ApiOperation("팔로워 목록 조회(로그인)")
	public Object getFollowersAndFlags (@PathVariable int userId, @PathVariable int profileId, 
			@RequestParam int page, @RequestParam int size, @RequestHeader String token) {
		
		Optional<User> UserOpt = userService.getUser(userId);
		Optional<User> profileUserOpt = userService.getUser(profileId);
		
		if (!UserOpt.isPresent() || !profileUserOpt.isPresent()) 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		
		try {
			if (userId == jwtService.getUserId(token)) { // 요청한 유저와 토큰 발급한 유저가 같다면
				
				List<FollowDto> results = followService.getFollowersAndFlags(UserOpt.get(), profileUserOpt.get(), page, size);
				return new ResponseEntity<>(results, HttpStatus.OK);

			} else { // 인증 실패
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) { // 유효하지 않은 토큰
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	
	
}
