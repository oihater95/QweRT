package com.web.qwert.controller.account;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.dao.user.UserDao;
import com.web.qwert.model.BasicResponse;
import com.web.qwert.model.user.SignupRequest;
import com.web.qwert.model.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

// @CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @PostMapping("/accounts/signup")
    @ApiOperation(value = "회원가입")
    public Object signup(@Valid @RequestBody User request) {
        Optional<User> userOpt = userDao.findUserByEmailOrNickname(request.getEmail(), request.getNickname());
        ResponseEntity response = null;
    	System.out.println("회원가입");
        // valid로 유효성 검증 실패시 403 return

    	if (!userOpt.isPresent()) { // 중복된 계정이 없다면
            User user = new User(request.getEmail(), request.getNickname(), request.getPassword());
            userDao.save(user);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
		return response;

    }
    
    @GetMapping("/accounts/emailcheck")
    @ApiOperation(value = "이메일 중복 검사")
    public Object emailCheck(@RequestParam(required = true) final String email) {
    	Optional<User> userOpt = userDao.findUserByEmail(email);
    	ResponseEntity response = null;
    	System.out.println("이메일 중복 검사");
    	
    	if (userOpt.isPresent()) { // 해당 이메일로 가입한 회원이 이미 있다면
    		response = new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	else {
    		response = new ResponseEntity<>(HttpStatus.OK);
    	}
		return response;
    }
    
    @GetMapping("/accounts/nicknamecheck")
    @ApiOperation(value = "닉네임 중복 검사")
    public Object nicknameCheck(@RequestParam(required = true) final String nickname) {
    	Optional<User> userOpt = userDao.findUserByNickname(nickname);
    	ResponseEntity response = null;
    	System.out.println("닉네임 중복 검사");
    	
    	if (userOpt.isPresent()) { // 해당 닉네임으로 가입한 회원이 이미 있다면
    		response = new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	else {
    		response = new ResponseEntity<>(HttpStatus.OK);
    	}
		return response;
    }
    
	
	  @GetMapping("/accounts/login")
	  @ApiOperation(value = "이메일과 비밀번호로 로그인합니다.") 
	  public Object login(@RequestParam(required = true) final String email,
	  
	  @RequestParam(required = true) final String password) {
	  
	  Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
	  ResponseEntity response = null;
	  
	  if (userOpt.isPresent()) { final BasicResponse result = new BasicResponse();
	  result.status = true; result.data = "success"; response = new
	  ResponseEntity<>(result, HttpStatus.OK); } else { response = new
	  ResponseEntity<>(null, HttpStatus.NOT_FOUND); }
	  
	  return response; }
	 
}