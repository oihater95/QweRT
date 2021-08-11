package com.web.qwert.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CurationDao;
import com.web.qwert.dao.CurationHasPostingDao;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.curation.CurationRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

@Service
public class CurationServiceImpl {
	
	@Autowired
	CurationDao curationDao;
	
	@Autowired 
	CurationHasPostingDao curationHasPostingDao;

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
	
	public Optional<CurationHasPosting> curateCheck (Curation curation, Posting posting) {
		return curationHasPostingDao.findByCurationAndPosting(curation, posting);
	}
	
	public void curatePosting (Curation curation, Posting posting) {
		CurationHasPosting curationHasPosting = new CurationHasPosting();
		curationHasPosting.setCuration(curation);
		curationHasPosting.setPosting(posting);
		curationHasPostingDao.save(curationHasPosting);
	}
	
	public void cancelCurate (CurationHasPosting curationHasPosting) {
		curationHasPostingDao.delete(curationHasPosting);
	}
}
