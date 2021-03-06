// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.qwert.model.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.qwert.model.Feed.Feed;
import com.web.qwert.model.comment.Comment;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.follow.Follow;
import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "nickname")
    private String nickname;
    
    @Column(name = "password")
    private String password;  
    
    // 가입일은 자동 입력
    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;
    
    // 소개와 프로필 사진은 회원 수정에서 입력
	@Column(name = "introduction")
    private String introduction;
    
    @Column(name = "profile_img")
    private String profileImg;
    
    @Builder
    public User(String email, String nickname, String password) {
		super();
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // posting의 user 객체 변수로 맵핑, 삭제시 post도 삭제
    @JsonIgnore
    private List<Posting> postings;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Like> likes;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Curation> curations;
    
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> followings;
    
    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> follower; 
    
    private int popularity;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Feed> feeds;


    
}
