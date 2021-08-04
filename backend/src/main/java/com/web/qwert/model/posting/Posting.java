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
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Posting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "posting_id")
	private int postingId;
	
	private String title;
	private String content;
	
	@JoinColumn(name = "posting_img")
	private String postingImg;
	
	// 가입일은 자동 입력
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate;
    
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    
    @JoinColumn(name = "masterpiece_flag")
    private boolean masterpieceFlag;
    
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "user_id") // FK
    @JsonIgnore
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;
}
