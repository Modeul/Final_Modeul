package com.modeul.web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.modeul.web.entity.Stuff;
import com.modeul.web.entity.StuffView;

public interface StuffService {
	
	// 레코드 행이 2개로 반환 되어서 List<Stuff> 형으로 타입 바꾸기
	void regStuff(Stuff stuff, List<MultipartFile> imgs);
	
	void regCrawlingStuff(Stuff stuff);
	
	
	List<StuffView> getViewAll();	// 전체 페이지 목록 조회용
	List<StuffView> getViewAll(int page);	// 페이지 더보기용
	List<StuffView> getViewAll(String query, int page);	// 검색용
	List<StuffView> getViewAll(Long categoryId, int page);	// 카테고리별 페이지 조회용
	List<StuffView> getViewAll(String query, Long categoryId, int page);	// 카테고리별 검색용 
	List<StuffView> getViewAll(String query, Long categoryId, int page, Long memberId);

	List<StuffView> getRecentViewList(Long categoryId, int page);
	List<StuffView> getRecentViewList(Long categoryId, int page, Long memberId, String dongCode);
	List<StuffView> getRecentViewList(String query, Long categoryId, int page);
	List<StuffView> getRecentViewList(String query, Long categoryId, int page, String dongCode);
	List<StuffView> getRecentViewList(String query, Long categoryId, int page, Long memberId);
	List<StuffView> getRecentViewList(String query, Long categoryId, int page, Long memberId, String dongCode);
	
	Stuff getById(Long id);
	StuffView getViewById(Long id);

	Long getListCount(Long categoryId, int page);
	Long getListCount(Long categoryId, int page, Long memberId);
	Long getListCount(Long categoryId, int page, Long memberId, String query);

	int editStuff(Stuff stuff, List<MultipartFile> imgs);

	void deleteStuff(Long id);



}
