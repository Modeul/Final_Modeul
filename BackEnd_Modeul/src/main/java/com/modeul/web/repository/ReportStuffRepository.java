package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.report;

@Mapper
public interface ReportRepository {
	
	List<report> findByProgress(char progress);
	List<report> findAll();
	
}
