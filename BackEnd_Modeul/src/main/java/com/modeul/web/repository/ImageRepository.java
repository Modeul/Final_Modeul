package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Image;

@Mapper
public interface ImageRepository {
	
	Image findById(Long stuffId);

	List<Image> findListById(Long stuffId);	
}
