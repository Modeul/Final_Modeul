package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.report;
import com.modeul.web.repository.ReportRepository;
@Service
public class ReportServiceimpl implements ReportService {

	@Autowired
	ReportRepository repository;
	@Override
	public List<report> getListByProgress(char progress) {
		
		return repository.findByProgress(progress);
	}
	@Override
	public List<report> getList() {
		
		return repository.findAll();
	}

}
