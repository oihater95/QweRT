package com.web.qwert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.comment.CommentDto;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.curation.CurationRequest;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
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
	public Object NewCuration(@PathVariable int userId, @RequestBody CurationRequest request,
			@RequestHeader String token) {

		Optional<User> userOpt = userService.getUser(userId);
		if (!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원

		try {
			if (userId == jwtService.getUserId(token)) { // 요청한 유저와 토큰 발급한 유저가 같다면
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

	@DeleteMapping("{curationId}")
	@ApiOperation("큐레이션 삭제")
	public Object deleteComment(@PathVariable int curationId, @RequestHeader String token) {

		Optional<Curation> curationOpt = curationService.getCuration(curationId);
		if (!curationOpt.isPresent())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 없는 댓글

		Curation curation = curationOpt.get();
		int userId = curation.getUser().getUserId();

		try {
			if (userId == jwtService.getUserId(token)) { // 큐레이터와 토큰 발급한 유저가 같다면
				curationService.deleteCuration(curation);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 권한 없음
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 유효하지 않은 토큰
		}

	}

	@PutMapping("{curationId}")
	@ApiOperation("큐레이션 수정")
	public Object updateCuration(@PathVariable int curationId, @RequestHeader String token,
			@RequestBody CurationRequest request) {

		Optional<Curation> curationOpt = curationService.getCuration(curationId);
		if (!curationOpt.isPresent())
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // 없는 댓글

		Curation curation = curationOpt.get();
		int userId = curation.getUser().getUserId();

		try {
			if (userId == jwtService.getUserId(token)) { // 큐레이터와 토큰 발급한 유저가 같다면
				curationService.updateCuration(curation, request);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 권한 없음
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 유효하지 않은 토큰
		}

	}

	@PostMapping("{curationId}/{postingId}")
	@ApiOperation("큐레이션에 게시글 추가")
	public Object curatePosting(@RequestHeader String token, @PathVariable int curationId,
			@PathVariable int postingId) {

		Optional<Curation> curationOpt = curationService.getCuration(curationId);
		Optional<Posting> postingOpt = postingService.getPosting(postingId);
		if (!curationOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 큐레이션
		if (!postingOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시글
		
		Curation curation = curationOpt.get();
		Posting posting = postingOpt.get();
		int userId = curation.getUser().getUserId();
		try {
			if (userId == jwtService.getUserId(token)) { // 요청한 유저와 큐레이션 주인이 같다면

				Optional<CurationHasPosting> curateOpt = curationService.curateCheck(curation, posting);
				if(curateOpt.isPresent()) return new ResponseEntity<>(HttpStatus.CONFLICT); // 이미 큐레이팅 됨 
				
				curationService.curatePosting(curation, posting);
				return new ResponseEntity<>(HttpStatus.OK); 

			} else { // 인증 실패
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) { // 유효하지 않은 토큰
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@DeleteMapping("{curationId}/{postingId}")
	@ApiOperation("큐레이션에서 게시글 삭제")
	public Object deletePostingInCuration(@RequestHeader String token, @PathVariable int curationId,
			@PathVariable int postingId) {

		Optional<Curation> curationOpt = curationService.getCuration(curationId);
		Optional<Posting> postingOpt = postingService.getPosting(postingId);
		if (!curationOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 큐레이션
		if (!postingOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시글
		
		Curation curation = curationOpt.get();
		Posting posting = postingOpt.get();
		int userId = curation.getUser().getUserId();
		try {
			if (userId == jwtService.getUserId(token)) { // 요청한 유저와 큐레이션 주인이 같다면

				Optional<CurationHasPosting> curateOpt = curationService.curateCheck(curation, posting);
				if(!curateOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 큐레이션에 게시글 없음 
				
				curationService.cancelCurate(curateOpt.get());
				return new ResponseEntity<>(HttpStatus.OK); 

			} else { // 인증 실패
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) { // 유효하지 않은 토큰
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@GetMapping("{curationId}")
	@ApiOperation("큐레이션 상세 조회")
	public Object watchCuration (@PathVariable int curationId) {
		Optional<Curation> curationOpt = curationService.getCuration(curationId);
		System.out.println(curationId);
		if(!curationOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 큐레이션
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("color", curationOpt.get().getColor());
		resultMap.put("postings", curationService.getCuratedPostings(curationOpt.get(), 0, 100));
		return new ResponseEntity<>(resultMap,HttpStatus.OK);
	}
	
//	@GetMapping("{userId}")
//	@ApiOperation("유저의 큐레이션 조회")
//	public Object getComments (@PathVariable int userId, @RequestParam int page, @RequestParam int size) {
//		Optional<User> userOpt = userService.getUser(userId);
//		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 게시물
//		
//		List<CommentDto> results = commentService.getComments(postingOpt.get(), page, size);
//		return new ResponseEntity<>(results, HttpStatus.OK);
//	}
}
