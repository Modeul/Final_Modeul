package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.ReportStuff;
import com.modeul.web.repository.ReportStuffRepository;
@Service
public class ReportServiceimpl implements ReportService {

	@Autowired
	ReportStuffRepository repository;


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
	@Override
	public String deleteReportedStuff(Integer id) {
		int resp = repository.delete(id);

		String result =  (resp != 1) ? "ERROR" : "OK";
		return result;
	}

}
