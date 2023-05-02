package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Crawling;
import com.modeul.web.repository.CrawlingRepository;

@Service
public class CrawlingServiceImpl implements CrawlingService {

    @Autowired
    private CrawlingRepository repository;

    // 조회용
    @Override
    public List<Crawling> getViewAll() {
        return repository.findAll(null, null, 7, 0);
    }

    @Override
    public List<Crawling> getViewAll(int page) {
        int size = page * 7;

        return repository.findAll(null, null, size, 0);
    }

    @Override
	public Long getListCount(Long categoryId, int page) {
		
		Long countList = repository.getCountList(categoryId) - (page * 7);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}

    @Override // 검색용
    public List<Crawling> getViewAll(String query, int page, Long categoryId) {
        int size = page * 7;
        return repository.findAll(query, categoryId, size, 0);
    }

    @Override
    public List<Crawling> getCategoryNameList(int page, Long categoryId) {
        int size = page * 7;
        return repository.findCategory(null, categoryId, size, 0);
    }


    
}
