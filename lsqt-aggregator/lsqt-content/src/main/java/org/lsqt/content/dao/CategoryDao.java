package org.lsqt.content.dao;

import java.io.Serializable;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;


public interface CategoryDao extends EntityDao<Category>{
	public boolean save(Category category);

	public Category update(Category category);

	public boolean deleteById(Serializable id);

	public Category findById(Serializable id) ;
	
	public void deleteAll();
	
	public Category getRoot();
	
}
