package com.web.qwert.model.posting;

import com.web.qwert.model.category.Category;

import lombok.Data;

@Data
public class UploadRequest {
	
	private int userId;
	private String postingTitle;
	private String postingContent;
	private String postingImage;
	private int categoryId;
}
