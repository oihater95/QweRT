package com.web.qwert.model.category;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.web.qwert.model.posting.Posting;

import lombok.Data;

@Entity
@Data
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int category_id;
	
	@Column(name = "category_name")
	private String category_name;
	
	@OneToMany(mappedBy = "category")
	private List<Posting> postings;
}
