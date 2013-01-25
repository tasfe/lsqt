package org.lsqt.content.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.CategoryDao;
import org.lsqt.content.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends AbstractHibernateDaoSupport<Category>  implements CategoryDao {
	
	@Override
	public Category findById(Serializable id) {
		final StringBuffer hql=new StringBuffer("from Category c where c.id= ? ");
		return (Category)super.uniqueResultByHql(hql.toString(), new Object[]{id});
	}
	
	/**
	 * 获取某个应用下的直接一级栏目(因栏目层级可嵌套).
	 * @param appID 应用ID
	 * 
	 * @return List 返回应用下的栏目
	 */
	public List<Category> getCategoryByApp(String appID){
		final StringBuffer hql=new StringBuffer("from Category c where c.appId=? and c.pid is null");
		return super.executeHqlQuery(hql.toString(), new Object[]{appID});
	}
}
