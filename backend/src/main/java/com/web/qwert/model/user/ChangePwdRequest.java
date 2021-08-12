package com.web.qwert.model.user;

import lombok.Data;

@Data
public class ChangePwdRequest {
	private String password;
	private String newPassword;
}
