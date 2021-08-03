package com.web.qwert.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.category.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer>{
	
}
