package org.lsqt.content.dao.impl;

import java.io.Serializable;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
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
	
	public Category getRoot(){
		final StringBuffer hql=new StringBuffer("from Category c where c.id=c.pid");
		return (Category)super.uniqueResultByHql(hql.toString());
	}
	
	public boolean hasRoot(){
		final StringBuffer hql=new StringBuffer("select count(*) from Category c where c.id=c.pid");
		Object obj=super.uniqueResultByHql(hql.toString());
		if(obj==null){
			return false;
		}else{
			return Integer.valueOf(obj.toString())>0;
		}
	}
}
