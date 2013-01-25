package org.lsqt.content.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Condition;
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
	
	/**
	 * 获取某个应用下的栏目(分页).
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public Page<Category> getCategoryByApp(String appID,Page<Category> initialPage){
		initialPage.addConditions(new Condition().eq("appId", appID));
		return categoryDao.loadPage(initialPage);
	}
	
	/**
	 * 获取某个应用下的直接一级栏目(因栏目层级可嵌套).
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public List<Category> getCategoryByApp(String appID){
		return this.categoryDao.getCategoryByApp(appID);
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

}
