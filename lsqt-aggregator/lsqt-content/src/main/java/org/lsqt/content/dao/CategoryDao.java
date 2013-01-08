package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.content.model.Category;


public interface CategoryDao extends EntityDao<Category>{
	
	/**
	 * 获取某个应用下的栏目.
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public List<Category> getCategoryByApp(String appID);
	
	public Category findById(Serializable id) ;
	
	public void deleteAll();
	
}
