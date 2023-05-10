package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Crawling;

@Mapper
public interface CrawlingRepository {
	
	// List<StuffView> findViewAll();	// 나중에 주석처리하기
	
	// 인자가 필요 없을 줄 알았는데 조회하기 위해서 카테고리별 조회나 검색용, 페이저 때문에 필요하다.
	// 조회용으로 데이터를 뽑아 낼 때, 사용하며 
	// 서비스 계층에서는 해당 All를 이용하여 업무상 여러 오버로드 메서스가 준비하여 사용하다.
	List<Crawling> findAll(		String query,
								Long categoryId, 
								Integer size, 
								Integer offset);

	Long getCountList(Long categoryId);

    List<Crawling> findCategory(String query,
								Long categoryId, 
								Integer size, 
								Integer offset,
								String categoryName);

    Crawling findbyId(long id);

}
