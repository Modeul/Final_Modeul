package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Crawling;

public interface CrawlingService {
    


    List<Crawling> getViewAll(); // 전체 페이지 목록 조회용
    List<Crawling> getViewAll(int page); // 페이지 더보기용
    List<Crawling> getViewAll(int page , Long categoryId); // 카테고리별 목록 조회용
    List<Crawling> getViewAll(String query, int page, Long categoryId); // 검색용



    Long getListCount(Long categoryId, int page);
    List<Crawling> getCategoryNameList(int page, String categoryName); //카테고리 목록
    

    Crawling getById(long id); // stuff조회
}
