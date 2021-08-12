package com.web.qwert.model.curation;

import java.util.List;

import lombok.Data;

@Data
public class CurationDto {
	
	private int curationId;
	
	private String title; // 큐레이션 제목
	
	private String content; // 내용
	
	private String thumbnail; // 썸네일 이미지 정보

    private String color; // 큐레이션 색
    
    private List<String> images; // 썸네일 없는 경우 최대 3개의 게시글 미리보기
}
