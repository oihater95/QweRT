package com.web.qwert.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;

public interface PostingService {
	public boolean createPosting(UploadRequest request);
	
	public List<Posting> getPostingsByUser(User user, int page, int size);
	
	public List<Posting> getNewPostings(int page, int size);
}
