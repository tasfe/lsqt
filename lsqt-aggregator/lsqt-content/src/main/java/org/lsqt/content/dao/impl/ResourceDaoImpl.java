package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.ResourceDao;
import org.lsqt.content.model.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDaoImpl extends AbstractHibernateDaoSupport<Resource> implements ResourceDao{
	public Resource getRoot(){
		final String hql="from Resource c where c.id=c.pid";
		return (Resource)super.uniqueResultByHql(hql);
	}
	
}
