package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Category;

public interface CategoryService {
	List<Category> getList();
	
	String getNameById(Long categoryId);
	
	Category getById(Long categoryId);

	int addCategory(Category category);

    int updateCategory(Category category);

    int delCategory(Long id);
}
