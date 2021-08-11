package com.web.qwert.model.posting;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import lombok.Data;

@Data
public class PostingDto {
	
	private int postingId;
	
	private String title; // 제목
	
	private String content; // 내용
	
	private String postingImg; // 그림 파일명
	
    private LocalDateTime createDate; // 게시일
    
    private LocalDateTime updateDate; // 수정일
    
    private int likeCnt; // 좋아요 받은 수 
    
    private int commentCnt; // 전제 일반 댓글 수
    
    private int docentCnt; // 전체 도슨트 댓글 수
    
    private int curatedCnt; // 큐레이팅 된 수
    
    private int userId; // 업로더 아이디
    
    private String nickname; // 업로더 닉네임
    
    private int categoryId; // 카테고리 아이디
}
