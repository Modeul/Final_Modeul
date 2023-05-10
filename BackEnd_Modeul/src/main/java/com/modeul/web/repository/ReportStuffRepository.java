package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.ReportStuff;

@Mapper
public interface ReportStuffRepository {
	
	List<ReportStuff> findAll();
	int insert(ReportStuff reportStuff);
	int delete(Integer id);
	
}
