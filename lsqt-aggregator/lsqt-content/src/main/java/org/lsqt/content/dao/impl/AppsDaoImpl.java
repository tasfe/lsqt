package org.lsqt.content.dao.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.AppsDao;
import org.lsqt.content.model.Application;
import org.springframework.stereotype.Repository;

@Repository
public class AppsDaoImpl extends AbstractHibernateDaoSupport<Application>  implements AppsDao{
	
	public int getMaxOrderNum(){
		StringBuffer hql=new StringBuffer();
		hql.append("select max(a.orderNum) from Application a");
		Object rs=super.uniqueResultByHql(hql.toString());
		
		return rs==null ? 0: Integer.valueOf(rs.toString());
	}
	
	public List<Application> findAll(){
		StringBuffer hql=new StringBuffer();
		hql.append("from Application a ");
		return super.executeHqlQuery(hql.toString());
	}
	
	public Page<Application> loadPage(String key,Page page){
		StringBuffer hql=new StringBuffer("from Application a where a.name like  '%"+StringEscapeUtils.escapeSql(key)+"%'  or a.description like  '%"+StringEscapeUtils.escapeSql(key)+"%' ");
		return super.loadPageByHql(hql.toString(), page);
	}
}
