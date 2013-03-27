package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;


public interface CategoryDao extends EntityDao<Category>{
	
	/**
	 * 获取某个栏目的下级栏目(一级).
	 * @param parentCategoryID 父栏目ID
	 * @return Page 返回应用下的栏目
	 */
	public Page<Category> getPageByPID(String parentCategoryID,Page page);
	
	/**
	 * 获取某个应用下的栏目.
	 * @param appID 应用ID
	 * @param page 不带数据的分页
	 * 
	 * @return Page 返回一个应用下的栏目分页
	 */
	public Page<Category> getPageByApp(String appID,Page page);
	
	/**
	 * 获取某个应用下的栏目.
	 * @param appID 应用ID
	 * 
	 * @return List 返回一个应用下所有栏目
	 */
	public List<Category> getCategoryByApp(String appID);
	
	/**
	 * 获到匹配关键字的栏目.
	 * @param keyWord 关键字
	 * @return 返回栏目分页
	 */
	public Page<Category> getPageByKey(String keyWord,Page page);
	
	/**
	 * 跟据类别ID获取类别分页.
	 * @param categoryID 类别ID
	 * @param page 不带数据的分页
	 * @return
	 */
	public Page<Category> getPageByID(String categoryID,Page page);
	
	
	public Category findById(Serializable id) ;
	
	public void deleteAll();
	
}
