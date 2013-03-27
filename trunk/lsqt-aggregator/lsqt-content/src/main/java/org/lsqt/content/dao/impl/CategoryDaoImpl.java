package org.lsqt.content.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
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
	public Page<Category> getPageByApp(String appID,Page page){
		final StringBuffer hql=new StringBuffer("from Category c where c.appId=? ");
		
		return super.loadPageByHql(hql.toString(), new Object[]{appID}, page);
	}
	
	@Override
	public Page<Category> getPageByPID(String categoryID,Page page){
		final StringBuffer hql=new StringBuffer("from Category c where  c.pid = ? ");
		return super.loadPageByHql(hql.toString(), new Object[]{categoryID}, page);
	}
	
	@Override
	public Page<Category> getPageByID(String categoryID,Page page){
		final StringBuilder hql=new StringBuilder("from Category c where  c.id = ?");
		return super.loadPageByHql(hql.toString(), new Object[]{categoryID},page);
	}
	
	@Override
	public Page<Category> getPageByKey(String keyWord,Page page){
		final StringBuffer hql=new StringBuffer();
		hql.append("select c from Category c where c.name like '%"+StringEscapeUtils.escapeSql(keyWord)+"%' or c.description like '%"+StringEscapeUtils.escapeSql(keyWord)+"%'");
		return super.loadPageByHql(hql.toString(), page);
	}
}
