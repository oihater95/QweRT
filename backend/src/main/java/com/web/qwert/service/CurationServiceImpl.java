package com.web.qwert.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CurationDao;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationRequest;
import com.web.qwert.model.user.User;

@Service
public class CurationServiceImpl {
	
	@Autowired
	CurationDao curationDao;

	public void createCuration(User user, CurationRequest request) {
		Curation curation = new Curation();
		BeanUtils.copyProperties(request, curation);
		if(curation.getColor() == null) curation.setColor("ffffff"); // 기본 색은 흰색
		curation.setUser(user);
		curationDao.save(curation);	
	}
	
	public Optional<Curation> getCuration (int curationId) {
		return curationDao.findById(curationId);
	}
	
	public void deleteCuration (Curation curation) {
		curationDao.delete(curation);
	}
	
	public void updateCuration(Curation curation, CurationRequest request) {
		BeanUtils.copyProperties(request, curation);
		if(curation.getColor() == null) curation.setColor("ffffff"); // 기본 색은 흰색
		curationDao.save(curation);
	}
}
