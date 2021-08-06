package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.PostingDto;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;

public interface PostingService {
	public boolean createPosting(UploadRequest request);
	
	public List<Posting> getPostingsByUser(User user, int page, int size);
	
	public List<Posting> getNewPostings(int page, int size);
	
	public List<Posting> getPopularPostings(int page, int size);
	
	public Optional<Posting> getPosting(int postingId);
	
	public PostingDto getPostingDetail(Posting posting);
}
