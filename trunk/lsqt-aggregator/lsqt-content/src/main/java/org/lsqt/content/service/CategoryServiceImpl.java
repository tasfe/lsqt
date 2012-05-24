package org.lsqt.content.service;


import javax.annotation.Resource;

import org.lsqt.content.dao.CategoryDao;
import org.lsqt.content.model.Category;
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
}
