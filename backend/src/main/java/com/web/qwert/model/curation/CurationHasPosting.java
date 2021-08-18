package com.web.qwert.model.curation;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.qwert.model.posting.Posting;

import lombok.Data;

@Entity
@Data
@Table(name="curation_has_posting")
public class CurationHasPosting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// 생성일은 자동 입력
    @Column(name = "curate_date", insertable = false, updatable = false)
	private LocalDateTime curateDate;
	
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "curation_id") // FK
    @JsonIgnore
	private Curation curation;
	
    @ManyToOne(fetch = FetchType.LAZY) // N+1 문제 방지
    @JoinColumn(name = "posting_id") // FK
    @JsonIgnore
	private Posting posting;
}
