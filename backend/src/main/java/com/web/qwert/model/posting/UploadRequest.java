package com.web.qwert.model.posting;

import com.web.qwert.model.category.Category;

import lombok.Data;

@Data
public class UploadRequest {
	
	private int user_id;
	private String posting_title;
	private String posting_content;
	private String posting_image;
	private int category_id;
}
