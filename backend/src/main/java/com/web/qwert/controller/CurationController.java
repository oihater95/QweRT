package com.web.qwert.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.curation.NewCurationRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;
import com.web.qwert.service.CurationServiceImpl;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.PostingService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/curations")
public class CurationController {
	
	@Autowired
	JwtService jwtService;

	@Autowired
	PostingService postingService;

	@Autowired
	UserService userService;
	
	@Autowired
	CurationServiceImpl curationService;
	
	@PostMapping("{userId}")
	@ApiOperation(value = "큐레이션 생성")
	public Object upload(@PathVariable int userId, @RequestBody NewCurationRequest request, 
			@RequestHeader String token) {

		Optional<User> userOpt = userService.getUser(userId);
		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원

		try {
			   if(userId == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 				
				   curationService.createCuration(userOpt.get(), request);
				   return new ResponseEntity<>(HttpStatus.OK);
				   
			   } else { // 권한 없음
				   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			   }
		   } catch (Exception e) { // 유효하지 않은 토큰
			   e.printStackTrace();
			   return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		   }
	}
}
