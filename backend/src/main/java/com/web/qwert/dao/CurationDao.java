package com.web.qwert.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.user.User;

@Repository
public interface CurationDao  extends JpaRepository<Curation, Integer>{
	
	List<Curation> findByUser(User user, Pageable pageable);
}
