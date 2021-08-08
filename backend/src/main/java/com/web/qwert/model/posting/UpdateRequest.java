package com.web.qwert.model.posting;

import lombok.Data;

@Data
public class UpdateRequest {
	private String postingTitle;
	private String postingContent;
	private int categoryId;
}
