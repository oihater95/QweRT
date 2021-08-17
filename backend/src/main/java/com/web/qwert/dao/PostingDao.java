package com.web.qwert.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.category.Category;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Repository
public interface PostingDao extends JpaRepository<Posting, Integer> {
	List<Posting> findPostingByUserAndMasterpieceFlag(User user, boolean masterpieceFlag);

	List<Posting> findByUser(User user, Pageable pageable);
	
	List<Posting> findByCategory(Category category, Pageable pageable);
	
	List<Posting> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(String content, String title, Pageable pageable);
	
	int countByUser(User user);
}
