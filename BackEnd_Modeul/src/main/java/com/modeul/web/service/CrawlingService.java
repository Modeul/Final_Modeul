package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Crawling;

public interface CrawlingService {
    


    List<Crawling> getViewAll(); // 전체 페이지 목록 조회용
    List<Crawling> getViewAll(int page); // 페이지 더보기용
    // List<Crawling> getViewAll(String query, int page);	// 검색용
	// List<Crawling> getViewAll(Long categoryId, int page);	// 카테고리별 페이지 조회용
    Long getListCount(Long categoryId, int page);
}
