package com.web.qwert.model.like;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
@Table(name="`like`") // 예약어 sql syntax error 방지
public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "like_id")
	private int likeId;
	
	@JoinColumn(name = "uploader_id")
	private int uploaderId; // 게시자 아이디 - 총 받은 좋아요수 구하는 용도
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	private User user; // 좋아요 준 유저
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posting_id")
	private Posting posting; // 좋아요 받은 게시물
}
