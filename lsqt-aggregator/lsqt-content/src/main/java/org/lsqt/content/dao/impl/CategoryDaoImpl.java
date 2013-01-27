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
	
	@Override
	public List<Category> getCategoryByApp(String appID){
		final StringBuffer hql=new StringBuffer("from Category c where c.appId=? and c.pid is null");
		return super.executeHqlQuery(hql.toString(), new Object[]{appID});
	}
	
	@Override
	public Page<Category> getCategoryByApp(String appID,Page page){
		final StringBuffer hql=new StringBuffer("from Category c where c.appId=? and c.pid is null");
		return super.loadPageByHql(hql.toString(), new Object[]{appID}, page);
	}
	
	@Override
	public Page<Category> getCategoryByPID(String categoryID,Page page){
		final StringBuffer hql=new StringBuffer("from Category c where  c.pid = ? ");
		return super.loadPageByHql(hql.toString(), new Object[]{categoryID}, page);
	}
}
