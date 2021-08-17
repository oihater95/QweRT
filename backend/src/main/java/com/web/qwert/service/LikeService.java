package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import com.web.qwert.model.like.Like;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

public interface LikeService {
	
	public Optional<Like> getLike (User user, Posting posting);
	
	public void updateLike (User user, Posting posting);
	
	public List<Like> getLikesByUser (User user, int page, int size);
}
