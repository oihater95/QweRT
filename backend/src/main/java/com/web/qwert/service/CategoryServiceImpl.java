package com.web.qwert.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.qwert.dao.CategoryDao;
import com.web.qwert.model.category.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public List<Category> getCategory() {
		return categoryDao.findAll();
	}
}
