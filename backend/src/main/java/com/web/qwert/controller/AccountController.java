package com.web.qwert.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.dao.UserDao;
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
        ResponseEntity response = null;
        System.out.println("회원가입");
        
        // valid로 유효성 검증 실패시 403 return
        
    	if (!userOpt.isPresent()) { // 중복된 계정이 없다면
    		userService.setUser(request);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
		return response;

    }
    
    @GetMapping("emailcheck")
    @ApiOperation(value = "이메일 중복 검사")
    public Object emailCheck(@RequestParam(required = true) final String email) {
    	Optional<User> userOpt = userService.getUserByEmail(email);
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
    
    @GetMapping("nicknamecheck")
    @ApiOperation(value = "닉네임 중복 검사")
    public Object nicknameCheck(@RequestParam(required = true) final String nickname) {
    	Optional<User> userOpt = userService.getUserByNickname(nickname);
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
    
	
	  @PostMapping("login")
	  @ApiOperation(value = "이메일과 비밀번호로 로그인합니다.") 
	  public Object login(@RequestBody LoginRequest request) {
	  
	  Optional<User> userOpt = userService.getUserByEmailAndPassword(request.getEmail(), request.getPassword());
	  ResponseEntity response = null;
	  Map<String, Object> resultMap = new HashMap<>();
	  System.out.println("로그인");
	  
	  if (userOpt.isPresent()) { 

			User user = userOpt.get();
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			String token = jwtService.create(userDto);

			resultMap.put("token", token);
			resultMap.put("user_id", user.getUser_id());
			resultMap.put("nickname", user.getNickname());
			resultMap.put("profile_image", user.getProfile_img());
	
		  response = new ResponseEntity<>(resultMap, HttpStatus.OK); 
	  } 
	  else { 
		  response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
	  }
	  return response; 
   }
	  
   @DeleteMapping("{user_id}")
   @ApiOperation(value = "회원 탈퇴")
   public Object signout (@PathVariable int user_id, HttpServletRequest request) {
	   ResponseEntity<?> response = null;
	   System.out.println(request.getHeader("token"));
	   try {
		   if(user_id == jwtService.getUserId(request.getHeader("token"))) { //요청한 유저와 토큰 발급한 유저가 같다면 
			   userService.deleteUser(user_id);
			   response = new ResponseEntity<>(HttpStatus.OK);
		   } else {
			   response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
		   response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	   }
	   return response; 
   }
   
   @PutMapping("{user_id}/pwd")
   @ApiOperation(value = "비밀번호 변경")
   public Object changePwd (@PathVariable int user_id, @RequestHeader String token, @RequestBody ChangePwdRequest request) {
	   
	   ResponseEntity<?> response = null;
	   Optional<User> userOpt = userService.getUser(user_id);

	   if(userOpt.isPresent()) { // 가입된 회원이면
		   try { 
			   if(user_id == jwtService.getUserId(token)) { //요청한 유저와 토큰 발급한 유저가 같다면 
				   User user = userOpt.get();

				   if(user.getPassword().equals(request.getPassword())) { //입력한 비밀번호가 맞다면
					   user.setPassword(request.getNew_password());
					   userService.setUser(user);
					   response = new ResponseEntity<>(HttpStatus.OK);
				   }
				   else {
					   response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 비밀번호 오류
				   }
				   
			   }
		   } catch (Exception e) { // 유효하지 않은 토큰이면
			   e.printStackTrace();
			   response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		   }
		   
	   } else {
		   response = new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 회원
	   }   
	   return response; 
   }
	 
   
}
