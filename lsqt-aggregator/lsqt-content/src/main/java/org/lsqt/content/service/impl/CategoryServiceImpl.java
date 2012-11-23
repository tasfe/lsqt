package org.lsqt.content.service.impl;


import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.CategoryDao;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{
	public CategoryDao categoryDao;

	@Resource
	public void setCategoryDao(CategoryDao categoryDaoImpl) {
		this.categoryDao = categoryDaoImpl;
	}
	
	public boolean save(Category category){
		return this.categoryDao.save(category);
	}

	public Category update(Category category){
		return this.categoryDao.update(category);
	}

	public boolean deleteById(String id){
		return this.categoryDao.deleteById(id);
	}

	public Category findById(String id) {
		return this.categoryDao.findById(id);
	}
	
	public void deleteAll() {
		this.categoryDao.deleteAll();
	}
	
	public Category getRoot(){
		return this.categoryDao.getRoot();
	}
	
	@SuppressWarnings("rawtypes")
	public Page loadPage(Page initialPage) {
		return this.categoryDao.loadPage(initialPage);
	}
	
	public boolean hasRoot(){
		return this.categoryDao.hasRoot();
	}
}
