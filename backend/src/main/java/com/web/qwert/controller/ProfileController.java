package com.web.qwert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.user.ProfileDto;
import com.web.qwert.model.user.User;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
    UserService userService;
	
	@GetMapping("{userId}")
	@ApiOperation("프로필 정보 불러오기")
	public Object getProfileInfo (@PathVariable int userId) {
		Optional<User> userOpt = userService.getUser(userId);
		if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
		System.out.println("프로필 상세 페이지");
		ProfileDto result = userService.getProfile(userOpt.get());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
