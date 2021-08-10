package com.web.qwert.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CurationDao;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.NewCurationRequest;
import com.web.qwert.model.user.User;

@Service
public class CurationServiceImpl {
	
	@Autowired
	CurationDao curationDao;

	public void createCuration(User user, NewCurationRequest request) {
		Curation curation = new Curation();
		BeanUtils.copyProperties(request, curation);
		if(curation.getColor() == null) curation.setColor("ffffff"); // 기본 색은 흰색
		curation.setUser(user);
		curationDao.save(curation);	
	}
}
