package com.web.qwert.model.follow;

import com.web.qwert.model.user.UserDto;

import lombok.Data;

@Data
public class FollowDto {
	
	private UserDto user;
	
	private boolean followFlag;
}
