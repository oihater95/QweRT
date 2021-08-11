package com.web.qwert.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.qwert.model.curation.Curation;

@Repository
public interface CurationDao  extends JpaRepository<Curation, Integer>{

}
