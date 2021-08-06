package com.web.qwert.service;

import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

public interface LikeService {
	
	public void updateLike (User user, Posting posting);
}
