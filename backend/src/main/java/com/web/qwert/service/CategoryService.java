package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import com.web.qwert.model.category.Category;

public interface CategoryService {
	public Optional<Category> getCategory(int categoryId);
	
	public List<Category> getAllCategory();
}