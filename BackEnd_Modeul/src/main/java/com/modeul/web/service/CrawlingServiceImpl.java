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

    static int pageSize = 8;

    // 조회용
    @Override
    public List<Crawling> getViewAll() {
        return repository.findAll(null, null, 0, 0);
    }
    @Override
    public List<Crawling> getViewAll(int page) {
		int size = page * pageSize;

        return repository.findAll(null, null, size, 0);
    }

    @Override
    public List<Crawling> getViewAll(int page , Long categoryId) {
		int size = page * pageSize;

        return repository.findAll(null, categoryId, size, 0);
    }

    @Override // 검색용
    public List<Crawling> getViewAll(String query, int page, Long categoryId) {
		int size = page * pageSize;
        return repository.findAll(query, categoryId, size, 0);
    }

    @Override
	public Long getListCount(Long categoryId, int page) {
		
		Long countList = repository.getCountList(categoryId) - (page * pageSize);
		Long result = countList <= 0 ? 0 : countList;
		return result;
	}


    @Override
    public List<Crawling> getCategoryNameList(int page, String categoryName) {
        int size = page * pageSize;
        return repository.findCategory(null, null, size, 0, categoryName);
    }

    @Override
    public Crawling getById(long id) {
        return repository.findbyId(id);
    }


    
}
