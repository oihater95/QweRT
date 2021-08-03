package com.web.qwert.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.PostingService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/postings")
public class PostingController {
		
	@Autowired
	JwtService JwtService;
	
	@Autowired
	PostingService postingService;
	
	@Autowired
	UserService userService;
	
	@PostMapping
    @ApiOperation(value = "게시물 업로드")
    public Object upload(@RequestBody UploadRequest request, @RequestHeader String token) {	
        ResponseEntity response = null;
    	System.out.println("업로드");
    	int user_id = request.getUser_id();
    	
    	try {
 		   if(user_id == JwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면
 			   
 			   if(postingService.createPosting(request)) { // 포스팅 성공
 	   
 				   response = new ResponseEntity<>(HttpStatus.OK);
 			   } else { // 비회원
 				  response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
 			   }
 		   } else {
 			   response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
 		   }
 		   
 	   	} catch (Exception e) { // 토큰 검증 실패
 		   e.printStackTrace();
 		   response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
 	   	}
		return response;
    }
	
	// 유저로 게시글 검색
	@GetMapping("{user_id}")
	@ApiOperation(value = "유저의 게시글 검색")
	public Object myPostings (@PathVariable int user_id, @RequestParam int page) {
		Optional<User> userOpt = userService.getUser(user_id);
		ResponseEntity response = null;
		System.out.println("내 게시물");
		System.out.printf("page = %d\n", page);
		
		if(userOpt.isPresent()) { // 회원이면
			List<Posting> result = postingService.getPostingsByUser(userOpt.get(), page);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
	
	// 카테고리로 게시글 검색
	// 유저 탈퇴시 게시글 전부 삭제
	
}
