package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;


public interface CategoryDao extends EntityDao<Category>{
	
	/**
	 * 获取某个栏目的一级下级栏目(因栏目层级可嵌套).
	 * @param parentCategoryID 父栏目ID
	 * @return Page 返回应用下的栏目
	 */
	public Page<Category> getCategoryByPID(String parentCategoryID,Page page);
	
	/**
	 * 获取某个应用下的直接一级栏目分页(因栏目层级可嵌套).
	 * @param appID 应用ID
	 * 
	 * @return Page 返回应用下的栏目
	 */
	public Page<Category> getCategoryByApp(String appID,Page page);
	
	/**
	 * 获取某个应用下的(一级)栏目.
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public List<Category> getCategoryByApp(String appID);
	
	public Category findById(Serializable id) ;
	
	public void deleteAll();
	
}
