package org.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Template;

/**
 * 模板基本信息持久化定义.
 * @author 袁明敏
 *
 */
public interface TemplateDao extends EntityDao<Template>{
	/**
	 * 获取某栏目下的模板排序号
	 * @param cateId 栏目id
	 * @return orderNum 某栏目下的模板排序号
	 */
	public int getMaxOrderNum(String cateId);
	
	/**
	 * 获取栏目下的模板数据(一层),
	 * @param categoryId  栏目id
	 * @param currPage 当前页
	 * @param perPageItems 每页显示记录数
	 * @return 分页数数据
	 */
	public Page<Template> getPageByCategoryId(String categoryId,int currPage, int perPageItems);
}
