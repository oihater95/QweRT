package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CategoryDao;
import com.web.qwert.model.category.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> getAllCategory() {
		return categoryDao.findAll();
	}

	@Override
	public Optional<Category> getCategory(int categoryId) {
		return categoryDao.findById(categoryId);
	}
}
