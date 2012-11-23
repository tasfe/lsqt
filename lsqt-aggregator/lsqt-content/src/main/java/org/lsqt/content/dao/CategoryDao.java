package org.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.content.model.Category;


public interface CategoryDao extends EntityDao<Category>{
  
	public void deleteAll();
	
	public Category getRoot();
	
	public boolean hasRoot();
}
