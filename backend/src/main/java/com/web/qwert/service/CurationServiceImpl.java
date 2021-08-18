package com.web.qwert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.qwert.dao.CurationDao;
import com.web.qwert.dao.CurationHasPostingDao;
import com.web.qwert.model.curation.Curation;
import com.web.qwert.model.curation.CurationDto;
import com.web.qwert.model.curation.CurationHasPosting;
import com.web.qwert.model.curation.CurationRequest;
import com.web.qwert.model.posting.Posting;
import com.web.qwert.model.user.User;
import com.web.qwert.model.user.UserDto;

@Service
public class CurationServiceImpl implements CurationService {
	
	@Autowired
	CurationDao curationDao;

	@Autowired
	CurationHasPostingDao curationHasPostingDao;

	@Override
	public void createCuration(User user, CurationRequest request) {
		Curation curation = new Curation();
		BeanUtils.copyProperties(request, curation);
		if (curation.getColor() == null)
			curation.setColor("ffffff"); // 기본 색은 흰색
		curation.setUser(user);
		curationDao.save(curation);
	}

	@Override
	public Optional<Curation> getCuration(int curationId) {
		return curationDao.findById(curationId);
	}

	@Override
	public void deleteCuration(Curation curation) {
		curationDao.delete(curation);
	}

	@Override
	public void updateCuration(Curation curation, CurationRequest request) {
		BeanUtils.copyProperties(request, curation);
		if (curation.getColor() == null)
			curation.setColor("ffffff"); // 기본 색은 흰색
		curationDao.save(curation);
	}

	@Override
	public Optional<CurationHasPosting> curateCheck(Curation curation, Posting posting) {
		return curationHasPostingDao.findByCurationAndPosting(curation, posting);
	}

	@Override
	public void curatePosting(Curation curation, Posting posting) {
		CurationHasPosting curationHasPosting = new CurationHasPosting();
		curationHasPosting.setCuration(curation);
		curationHasPosting.setPosting(posting);
		curationHasPostingDao.save(curationHasPosting);
	}

	@Override
	public void cancelCurate(CurationHasPosting curationHasPosting) {
		curationHasPostingDao.delete(curationHasPosting);
	}
	
	// 해당 게시글의 큐레이팅 된 횟수 가져오기 
	@Override
	public int getCuratedCnt(Posting posting) {
		return curationHasPostingDao.countByPosting(posting);
	}
	
	// 해당 유저의 총 큐레이팅 된 횟수 구하기
	@Override
	public int getTotalCuratedCnt(User user) {
		List<Posting> postings = user.getPostings();
		
		int totalCnt = 0;
		for(Posting posting : postings) {
			totalCnt += getCuratedCnt(posting);
		}
		
		return totalCnt;
	}
	
	// 큐레이팅 된 게시글 리스트 가져오기
	@Override
	public List<Posting> getCuratedPostings(Curation curation, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.by("curateDate")); // 먼저 큐레이팅 된 순으로 게시글 정렬
		List<CurationHasPosting> curationHasPostings = curationHasPostingDao.findByCuration(curation, pageable);

		List<Posting> postings = new ArrayList<Posting>();
		for (CurationHasPosting curatedPosting : curationHasPostings) {
			postings.add(curatedPosting.getPosting());
		}
		return postings;
	}

	// 최신 큐레이션 가져오기
	@Override
	public List<CurationDto> getNewCurations(int page, int size) {
		List<CurationDto> curationDtos = new ArrayList<CurationDto>();
		// 최신 큐레이션을 페이징해서 가져온다.
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
		List<Curation> curations = curationDao.findAll(pageable).getContent();

		// 응답용 큐레이션 겍체 리스트 생성
		for (Curation curation : curations) {
			
			// 큐레이션 정보 저장
			CurationDto curationDto = new CurationDto();
			BeanUtils.copyProperties(curation, curationDto);
			
			// 큐레이터 정보 저장
			UserDto curator = new UserDto();
			BeanUtils.copyProperties(curation.getUser(), curator);
			curationDto.setUser(curator);
			
			// 썸네일이 없는 큐레이션은 미리보기 게시글 최대 3개 추가한다.
			if (curation.getThumbnailImg() == null) {

				List<Posting> postings = getCuratedPostings(curation, 0, 3);
				List<String> images = new ArrayList<String>();
				for (Posting posting : postings) {
					images.add(posting.getPostingImg());
				}
				curationDto.setImages(images);
			}
			else { // 썸네일 저장
				curationDto.setThumbnail(curation.getThumbnailImg());
			}
			
			curationDtos.add(curationDto);
		}
		return curationDtos;
	}

	// 특정 유저의 큐레이션 가져오기
	@Override
	public List<CurationDto> getCurationsByUser(User user, int page, int size) {
		List<CurationDto> curationDtos = new ArrayList<CurationDto>();
		// 유저의 큐레이션을 페이징해서 가져온다.
		Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());  // 최신 순으로 큐레이션 정렬
		List<Curation> curations = curationDao.findByUser(user, pageable);

		// 응답용 큐레이션 겍체 리스트 생성
		for (Curation curation : curations) {
			// 큐레이션 정보 저장
			CurationDto curationDto = new CurationDto();
			BeanUtils.copyProperties(curation, curationDto);		
			
			// 썸네일이 없는 큐레이션은 미리보기 게시글 최대 3개 추가한다.
			if (curation.getThumbnailImg() == null) {

				List<Posting> postings = getCuratedPostings(curation, 0, 3);
				List<String> images = new ArrayList<String>();
				for (Posting posting : postings) {
					images.add(posting.getPostingImg());
				}
				curationDto.setImages(images);
			}
			else { // 썸네일 저장
				curationDto.setThumbnail(curation.getThumbnailImg());
			}
			
			curationDtos.add(curationDto);
		}
		return curationDtos;
	}

}
