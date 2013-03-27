package org.lsqt.content.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.CategoryDao;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService{
	public CategoryDao categoryDao;

	@Resource
	public void setCategoryDao(CategoryDao categoryDaoImpl) {
		this.categoryDao = categoryDaoImpl;
	}
	
	
	@Override
	public Page<Category> getPageByPID(String parentCategoryID,Page initPage){
		return this.categoryDao.getPageByPID(parentCategoryID, initPage);
	}
	
	@Override
	public List<Category> getCategoryByApp(String appID){
		return this.categoryDao.getCategoryByApp(appID);
	}
	
	@Override
	public Page<Category> getPageByApp(String appID,Page initPage){
		return this.categoryDao.getPageByApp(appID,initPage);
	}
	
	@Override
	public Page<Category> getPageByID(String categoryID,Page initPage){
		return this.categoryDao.getPageByID(categoryID,initPage);
	}
	
	@Override
	@Transactional
	public boolean save(Category category){
		return this.categoryDao.save(category);
	}
	
	@Override
	@Transactional
	public Category update(Category category){
		return this.categoryDao.update(category);
	}

	@Override
	@Transactional
	public boolean deleteById(String id){
		return this.categoryDao.deleteById(id);
	}

	@Override
	public Category findById(String id) {
		return this.categoryDao.findById(id);
	}

	@Override
	public Page<Category> getPageByKey(String keyWord, Page initPage)	{
		return categoryDao.getPageByKey(keyWord, initPage);
	}
}
