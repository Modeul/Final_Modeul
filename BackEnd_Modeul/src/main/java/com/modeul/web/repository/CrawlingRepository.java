package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Crawling;

@Mapper
public interface CrawlingRepository {

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
