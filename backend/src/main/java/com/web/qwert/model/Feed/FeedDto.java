package com.web.qwert.model.Feed;

import java.time.LocalDateTime;

import com.web.qwert.model.user.UserDto;

import lombok.Data;

@Data
public class FeedDto {
	
	private UserDto user; // 게시자 정보
	
	private int postingId; // 게시글 아이디
	
	private String title; // 게시글 제목
	
	private String postingImg; // 게시글 이미지
	
    private int likeCnt; // 좋아요 받은 수 
    
    private int commentCnt; // 전제  댓글 수
 
    private int curatedCnt; // 큐레이팅 된 수
}
