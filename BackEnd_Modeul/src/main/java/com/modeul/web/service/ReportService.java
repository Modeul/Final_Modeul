package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.ReportStuff;

public interface ReportService {
	

	List<ReportStuff> getReportedStuffList();
	
	String regReportedStuff(ReportStuff reportStuff);

	String deleteReportedStuff(Integer id, String c);

}
