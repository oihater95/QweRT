package com.web.qwert.model.posting;

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
import com.web.qwert.model.category.Category;
import com.web.qwert.model.user.User;

import lombok.Data;

@Entity
@Data
public class Posting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int posting_id;
	private String title;
	private String content;
	private String posting_img;
	// 가입일은 자동 입력
    @Column(insertable = false, updatable = false)
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    
    private boolean masterpiece_flag;
    
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
}
