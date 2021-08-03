package com.web.qwert.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.posting.Posting;

@Repository
public interface PostingDao extends JpaRepository<Posting, Integer> {

}
