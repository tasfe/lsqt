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
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY,region="categoryCache")
public class CategoryServiceImpl implements CategoryService{
	
	@Override
	public Page<Category> getCategoryByPID(String parentCategoryID,Page page){
		return this.categoryDao.getCategoryByPID(parentCategoryID, page);
	}
	
	@Override
	public List<Category> getCategoryByApp(String appID){
		return this.categoryDao.getCategoryByApp(appID);
	}
	
	@Override
	public Page<Category> getCategoryByApp(String appID,Page page){
		return this.categoryDao.getCategoryByApp(appID,page);
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
	
	
	public CategoryDao categoryDao;

	@Resource
	public void setCategoryDao(CategoryDao categoryDaoImpl) {
		this.categoryDao = categoryDaoImpl;
	}
}
