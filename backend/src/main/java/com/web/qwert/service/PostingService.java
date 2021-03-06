package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.web.qwert.model.category.Category;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.posting.PostingDto;
import com.web.qwert.model.posting.UpdateRequest;
import com.web.qwert.model.posting.UploadRequest;
import com.web.qwert.model.user.User;

public interface PostingService {
	public void createPosting(UploadRequest request);
	
	public List<Posting> getPostingsByUser(User user, int page, int size);
	
	public List<Posting> getNewPostings(int page, int size);
	
	public List<Posting> getPopularPostings(int page, int size);
	
	public List<Posting> getFavoritePostings(User user, int page, int size);
	
	public Optional<Posting> getPosting(int postingId);
	
	public PostingDto getPostingDetail(Posting posting);
	
	public void removePosting(Posting posting);
	
	public void updatePosting(Posting posting, UpdateRequest request);
	
	public List<Posting> searchNewByCategory(Category category, int page, int size);
	
	public List<Posting> searchPopularByCategory(Category category, int page, int size);
	
	public List<Posting> searchNewByTerm(String term, int page, int size);
	
	public List<Posting> searchPopularByTerm(String term, int page, int size);
}
