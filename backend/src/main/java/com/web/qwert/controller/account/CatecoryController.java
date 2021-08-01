package com.web.qwert.controller.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.category.Category;
import com.web.qwert.service.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CatecoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public Object getCategoryList() {
		
		List<Category> list = categoryService.getCategory();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
