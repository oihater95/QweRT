package com.web.qwert.model.curation;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
public class Curation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "curation_id")
	private int curationId;
	
	private String title; // 큐레이션 제목
	private String content; // 내용
	
	@JoinColumn(name = "thumbnail_img")
	private String thumbnailImg; // 썸네일 이미지 정보
	
	// 가입일은 자동 입력
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate; // 게시일
    
    private String color; // 큐레이션 색
    
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private User user; // 큐레이터 
}
