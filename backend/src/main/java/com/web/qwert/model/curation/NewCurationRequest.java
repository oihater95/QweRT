package com.web.qwert.model.curation;

import lombok.Data;

@Data
public class NewCurationRequest {
	
	private String title;
	private String content;
	private String thumbnailImg;
	private String color;
}
