package org.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends AbstractHibernateDaoSupport<Category>  implements CategoryDao {
	public Category getRoot(){
		final String hql="from Category c where c.id=c.pid";
		return (Category)super.uniqueResultByHql(hql);
	}
}
