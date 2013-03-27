package org.lsqt.content.service;

import java.util.List;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;

public interface CategoryService {
	
	/**
	 * 获取某个栏目的一级栏目.
	 * @param parentCategoryID 父栏目ID
	 * @return Page 返回应用下的栏目数据
	 */
	public Page<Category> getPageByPID(String categoryID,Page initPage);
	
	/**
	 * 获取某个应用下的一级栏目.
	 * @param appID 应用ID
	 * 
	 * @return Page 返回某应用下的(一级)栏目数据
	 */
	public Page<Category> getPageByApp(String appID,Page initPage);
	
	/**
	 * 跟据类别ID获取类别分页.
	 * @param categoryID 类别ID
	 * @param page 不带数据的分页
	 * @return 一条数据的分页
	 */
	public Page<Category> getPageByID(String categoryID,Page initPage);
	
	/**
	 * 获取匹配关键字的栏目.
	 * @param keyWord 关键字
	 * @return 返回栏目分页
	 */
	public Page<Category> getPageByKey(String keyWord,Page initPage);
	
	/**
	 * 获取某个应用下的栏目(一级).
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public List<Category> getCategoryByApp(String appID);
	
	public boolean save(Category category);

	public Category update(Category category);

	public boolean deleteById(String id);

	public Category findById(String id) ;
	
}
