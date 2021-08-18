package com.web.qwert.model.follow;

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
public class Follow {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "follow_id")
	private int followId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id") 
	@JsonIgnore
	private User fromUser; // 팔로우 한 유저
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id") 
	@JsonIgnore
	private User toUser; // 팔로우 받은 유저
}
