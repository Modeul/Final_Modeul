package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modeul.web.entity.ReportStuff;
import com.modeul.web.repository.ReportStuffRepository;
@Service
public class ReportServiceimpl implements ReportService {

	@Autowired
	ReportStuffRepository repository;
	
	@Autowired
	StuffServiceImpl stuffService;


	@Override
	public List<ReportStuff> getReportedStuffList() {
		return repository.findAll();
	}
	@Override
	public String regReportedStuff(ReportStuff reportStuff) {
		int resp = repository.insert(reportStuff);

		String result =  (resp != 1) ? "ERROR" : "OK";
		return result;
	}
	// @Transactional
	@Override
	public String deleteReportedStuff(Integer id, String c) {
		
		if (c != null) {
			return (repository.delete(id) != 1) ? "ERROR" : "OK"; 
			
		} else {
			stuffService.deleteStuff(Long.valueOf(id));
			return "OK";
		}
		
		// int resp = repository.delete(id);  -- ON DELETE CASCADE
		// String result =  (resp != 0) ? "ERROR" : "OK";

	}

}
