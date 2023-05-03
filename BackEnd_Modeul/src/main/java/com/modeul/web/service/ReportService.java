package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.report;

public interface ReportService {
	List<report> getListByProgress(char progress);
	List<report> getList();
}
