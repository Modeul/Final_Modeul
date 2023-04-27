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
        return repository.findAll(null, null, null);
    }

    @Override
    public List<Crawling> getViewAll(int page) {
        int size = 10;
        int offset = (page-1)*10;

        return repository.findAll(null, size, offset);
    }
    // @Override
	// public List<Crawling> getViewAll(Long categoryId, int page) {
	// 	int size = 10;
	// 	int offset = (page-1)*10;
		
	// 	return repository.findAll(null, categoryId, null, null, size, offset);
	// }
}
