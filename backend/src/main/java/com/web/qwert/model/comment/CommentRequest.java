package com.web.qwert.model.comment;

import lombok.Data;

@Data
public class CommentRequest {
	
	private String content;
	private boolean docentFlag;
	private int userId;
}
