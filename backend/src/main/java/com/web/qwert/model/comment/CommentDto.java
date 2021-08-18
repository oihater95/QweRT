package com.web.qwert.model.comment;

import java.time.LocalDateTime;

import com.web.qwert.model.user.UserDto;

import lombok.Data;

@Data
public class CommentDto {
	
	private int commentId;
	
	private String content;
	
    private LocalDateTime createDate;
    
	private LocalDateTime updateDate;
	
	private UserDto user; // 댓글 작성자 정보
}
