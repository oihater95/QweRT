package com.web.qwert.model.user;

import lombok.Data;

@Data
public class ChangeInfoRequest {
	private String nickname;
	private String introduction;
	private String profileImage;
	private int[] masterpieceIds;
	
}
