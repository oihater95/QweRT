package com.web.qwert.service;

import java.util.List;
import java.util.Optional;

import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationDto;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.curation.CurationRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;

public interface CurationService {

	void createCuration(User user, CurationRequest request);

	Optional<Curation> getCuration(int curationId);

	void deleteCuration(Curation curation);

	void updateCuration(Curation curation, CurationRequest request);

	Optional<CurationHasPosting> curateCheck(Curation curation, Posting posting);

	void curatePosting(Curation curation, Posting posting);

	void cancelCurate(CurationHasPosting curationHasPosting);

	// 게시글의 큐레이팅 된 수 구하기
	int getCuratedCnt(Posting posting);
	
	// 유저의 총 큐레이팅 된 수 구하기
	int getTotalCuratedCnt(User user);
	
	// 큐레이팅 된 게시글 리스트 가져오기
	List<Posting> getCuratedPostings(Curation curation, int page, int size);

	// 최신 큐레이션 가져오기
	List<CurationDto> getNewCurations(int page, int size);

	// 특정 유저의 큐레이션 가져오기
	List<CurationDto> getCurationsByUser(User user, int page, int size);

}