package com.web.qwert.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.ChangeInfoRequest;
import com.web.qwert.model.user.ChangePwdRequest;
import com.web.qwert.model.user.LoginRequest;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;
import com.web.qwert.service.JwtService;
import com.web.qwert.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Failure") })

// 스웨거 주소
// http://localhost:80/qwert/swagger-ui.html

// CORS 허용
//@CrossOrigin(origins = { "http://localhost:8080" })
@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class AccountController {
	
    @Autowired
    UserService userService;
    
	@Autowired
	private JwtService jwtService;

    @PostMapping("signup")
    @ApiOperation(value = "회원가입")
    public Object signup(@Valid @RequestBody User request) {
    	Optional<User> userOpt = userService.getUser(request.getEmail(), request.getNickname());
        System.out.println("회원가입");
        
        // valid로 유효성 검증 실패시 403 return
        
    	if (!userOpt.isPresent()) { // 중복된 계정이 없다면
    		userService.setUser(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("emailcheck")
    @ApiOperation(value = "이메일 중복 검사")
    public Object emailCheck(@RequestParam(required = true) final String email) {
    	Optional<User> userOpt = userService.getUserByEmail(email);
    	System.out.println("이메일 중복 검사");
    	
    	if (userOpt.isPresent()) { // 해당 이메일로 가입한 회원이 이미 있다면
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    }
    
    @GetMapping("nicknamecheck")
    @ApiOperation(value = "닉네임 중복 검사")
    public Object nicknameCheck(@RequestParam(required = true) final String nickname) {
    	Optional<User> userOpt = userService.getUserByNickname(nickname);
    	System.out.println("닉네임 중복 검사");
    	
    	if (userOpt.isPresent()) { // 해당 닉네임으로 가입한 회원이 이미 있다면
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	else {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    }
    
	
	  @PostMapping("login")
	  @ApiOperation(value = "이메일과 비밀번호로 로그인합니다.") 
	  public Object login(@RequestBody LoginRequest request) {
	  
	  Optional<User> userOpt = userService.getUserByEmailAndPassword(request.getEmail(), request.getPassword());
	  Map<String, Object> resultMap = new HashMap<>();
	  System.out.println("로그인");
	  
	  if (userOpt.isPresent()) { 

			User user = userOpt.get();
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			String token = jwtService.create(userDto);

			resultMap.put("token", token);
			resultMap.put("userId", user.getUserId());
			resultMap.put("nickname", user.getNickname());
			resultMap.put("profileImage", user.getProfileImg());
	
		  	return new ResponseEntity<>(resultMap, HttpStatus.OK); 
	  } 
	  else { 
		  	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	  }
	   
   }
	  
   @DeleteMapping("{user_id}")
   @ApiOperation(value = "회원 탈퇴")
   public Object signout (@PathVariable int user_id, HttpServletRequest request) {
	   Optional<User> userOpt = userService.getUser(user_id);
	   if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
	   
	   try {
		   if(user_id == jwtService.getUserId(request.getHeader("token"))) { //요청한 유저와 토큰 발급한 유저가 같다면 
			   userService.deleteUser(user_id);
			   return new ResponseEntity<>(HttpStatus.OK);
		   } else {
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
		   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	   }
   }
   
   @PutMapping("{user_id}/pwd")
   @ApiOperation(value = "비밀번호 변경")
   public Object changePwd (@PathVariable int user_id, @RequestHeader String token, @RequestBody ChangePwdRequest request) {
	   
	   Optional<User> userOpt = userService.getUser(user_id);
	   if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
	   
	   try { 
		   if(user_id == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
			   User user = userOpt.get();

			   if(user.getPassword().equals(request.getPassword())) { //입력한 비밀번호가 맞다면
				   user.setPassword(request.getNewPassword());
				   userService.setUser(user);
				   return new ResponseEntity<>(HttpStatus.OK);
			   }
			   else { // 비밀번호 오류
				   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
			   }
			   
		   } else { // 토큰 도용
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
		   
	   } catch (Exception e) { // 유효하지 않은 토큰이면
		   e.printStackTrace();
		   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	   }
   }
   
   @PutMapping("{user_id}/info")
   @ApiOperation("회원정보 변경")
   public Object changeInfo (@PathVariable int user_id, @RequestHeader String token, @RequestBody ChangeInfoRequest request) {
	   Optional<User> userOpt = userService.getUser(user_id);   
	   if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 비회원
	   Map<String, Object> resultMap = new HashMap<>();
	   Posting[] postings = null;
	   try {
		   if(user_id == jwtService.getUserId(token)) {  
			   resultMap.put("nickname", request.getNickname());
			   resultMap.put("introduction", request.getIntroduction());
			   resultMap.put("profileImage", request.getProfileImage());
			   postings = userService.updateUserInfo(userOpt.get(), request);
			   resultMap.put("masterpieces", postings);
			   return new ResponseEntity<>(resultMap, HttpStatus.OK);
			   
		   } else { // 도용된 토큰
			   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
		   
	   } catch (Exception e) { // 유효하지 않은 토큰
		   e.printStackTrace();
		   return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	   }
	   
	   
   }

}
