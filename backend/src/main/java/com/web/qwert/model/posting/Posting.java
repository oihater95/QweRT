package com.web.qwert.model.posting;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.qwert.model.Feed.Feed;
import com.web.qwert.model.category.Category;
import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 좋아요한 게시물 리스트 리턴시 직렬화 에러 해결
public class Posting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "posting_id")
	private int postingId;
	
	private String title; // 제목
	private String content; // 내용
	
	@JoinColumn(name = "posting_img")
	private String postingImg; // 그림 파일명
	
	// 가입일은 자동 입력
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate; // 게시일
    
    @Column(name = "update_date")
    private LocalDateTime updateDate; // 수정일
    
    @JoinColumn(name = "masterpiece_flag")
    private boolean masterpieceFlag; // 대표작 유무
    
    @JoinColumn(name = "like_cnt")
    private int likeCnt; // 좋아요 받은 수 - 인기 순 정렬 용도
    
    
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
    
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Like> likes;
    
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CurationHasPosting> curationHasPostings;
    
    @OneToMany(mappedBy = "posting", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feed> Feeds;
}
