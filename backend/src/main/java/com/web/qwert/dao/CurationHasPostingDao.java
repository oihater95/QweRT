package com.web.qwert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.posting.Posting;

@Repository
public interface CurationHasPostingDao extends JpaRepository<CurationHasPosting, Integer>{
	
	Optional<CurationHasPosting> findByCurationAndPosting (Curation curation, Posting posting);
	
	int countByPosting (Posting posting);
	
	List<CurationHasPosting> findByCuration (Curation curation, Pageable pageable);
}
