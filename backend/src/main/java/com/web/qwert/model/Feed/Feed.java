package com.web.qwert.model.Feed;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
public class Feed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "feed_id")
	private int feedId;	
	
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate; // 피드 생성일
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	@JsonIgnore
	private User user; // 피드 대상
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posting_id")
	@JsonIgnore
	private Posting posting; // 피드된 게시물
}
