package com.web.qwert.dao.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.qwert.model.category.Category;

public interface CategoryDao extends JpaRepository<Category, Integer>{
	
}
