package com.web.qwert.model.user;

import com.web.qwert.model.posting.Posting;

import lombok.Data;

@Data
public class ProfileDto {
	
	private int userId;
	private String nickname;
	private String introduction;
	private String profileImg; 
	private int followState; // 팔로우 유무
	private int followerCnt; // 팔로워의 수
	private int followingCnt; // 팔로잉한 수
	private int curatedCnt; // 큐레이션 된 수
	private int likedCnt; // 좋아요 받은 수
	private int postingCnt; // 게시물 수
	private Posting[] masterpieces; // 대표작
	
}
