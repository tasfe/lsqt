package org.lsqt.content.service;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Template;
import org.lsqt.content.model.TmplContent;

/**
 * 
 * @author 袁明敏
 *
 */
public interface TemplateService{
	
	/**
	 * 获取某栏目下的模板排序号
	 * @param cateId 栏目id
	 * @return orderNum 某栏目下的模板排序号
	 */
	public int getMaxOrderNum(String cateId);
	
	/**
	 * 保存模板基本信息和内容.
	 * @param tempate 模板基本信息
	 * @param content 模板byte内容
	 */
	public void save(Template template, TmplContent content);
	
	/**
	 * 获取栏目下的模板数据(一层),
	 * @param categoryId  栏目id
	 * @param currPage 当前页
	 * @param perPageItems 每页显示记录数
	 * @return 分页数数据
	 */
	public Page getPageByCategoryId(String categoryId,int currPage,int perPageItems);
	
	/**
	 * 删除模板基本信息并删除表示的物理磁盘文件.
	 * @param id 模板标识
	 */
	public void deleteById(String id);
}
