package com.web.qwert.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.qwert.model.category.Category;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.service.CategoryService;
import com.web.qwert.service.PostingService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	PostingService postingService;
	
	@Autowired
	CategoryService categoryService;
	
	// 카테고리로 인기 게시글 검색
	@GetMapping("popular/category/{categoryId}")
	@ApiOperation(value = "카테고리로 인기 게시글 검색")
	public Object popularPostings(@PathVariable int categoryId, @RequestParam int page, @RequestParam int size) {
		
		Optional<Category> categoryOpt = categoryService.getCategory(categoryId);
		if (!categoryOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 카테고리
		
		List<Posting> result = postingService.searchPopularByCategory(categoryOpt.get(), page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	// 카테고리로 최신 게시글 검색
	@GetMapping("new/category/{categoryId}")
	@ApiOperation(value = "카테고리로 최신 게시글 검색")
	public Object newPostings(@PathVariable int categoryId, @RequestParam int page, @RequestParam int size) {
		Optional<Category> categoryOpt = categoryService.getCategory(categoryId);
		if (!categoryOpt.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 없는 카테고리
		
		List<Posting> result = postingService.searchNewByCategory(categoryOpt.get(), page, size);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
