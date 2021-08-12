package com.web.qwert.model.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
@Table(name="`comment`") // 예약어 sql syntax error 방지
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "comment_id")
	private int commentId;
	
	private String content;
	
	//@CreatedDate 
	@Column(name = "create_date", insertable = false, updatable = false) // 가입일은 자동 입력
    private LocalDateTime createDate; // 게시일
    
	@JoinColumn(name = "update_date")
	private LocalDateTime updateDate;
	
	@JoinColumn(name = "docent_flag")
	private boolean docentFlag;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") 
	@JsonIgnore
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posting_id")
	@JsonIgnore
	private Posting posting;
}
