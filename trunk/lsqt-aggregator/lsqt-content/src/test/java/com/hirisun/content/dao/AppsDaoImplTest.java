package com.hirisun.content.dao;

import java.util.List;

import org.junit.Test;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;

public class AppsDaoImplTest extends AbstractHibernateDaoSupportTest
{
	/**
	 * Testing: Page<Application> loadPage(String key,Page page)
	 */
	@Test
	public void loadPage(){
		String key="test";
		StringBuffer hql=new StringBuffer("from Application a where a.name like  ?  or a.description like ? ");
		getSession().createQuery(hql.toString()).setParameter(0,"'%"+key+"%'").setParameter(1,"'%"+key+"%'");
		
		StringBuffer countStr = new StringBuffer("select count(*)  ");
		countStr.append(hql).append("  ");
		Object obj=getSession().createQuery(countStr.toString())
				.setParameter(0, "'%" + key + "%'")
				.setParameter(1, "'%" + key + "%'").uniqueResult();
		if(obj!=null){
			Integer.valueOf(obj.toString());
		}
	}
	
	@Test
	public void getAppsByKey(){
		String key="t";
		StringBuffer hql=new StringBuffer("select distinct a.name from Application a where a.name like  '%"+key+"%'  or a.description like  '%"+key+"%' ");
		List list=super.executeHqlQuery(hql.toString());
		System.out.println(list);
		
		System.out.println(getSession().createQuery(hql.toString()).list());
	}
}
