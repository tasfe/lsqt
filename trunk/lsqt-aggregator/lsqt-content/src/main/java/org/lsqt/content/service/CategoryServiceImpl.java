package org.lsqt.content.service;

import org.lsqt.content.dao.CategoryDao;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
	public CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
}
