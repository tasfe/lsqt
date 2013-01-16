package org.lsqt.content.dao.impl;

import java.util.List;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.NewsDao;
import org.lsqt.content.model.News;
import org.lsqt.content.model.User;
import org.springframework.stereotype.Repository;


@Repository
public class NewsDaoImpl extends AbstractHibernateDaoSupport<News> implements NewsDao{
	
	@Override
	public List<News> getNewsByApp(String appID){
		StringBuffer hql=new StringBuffer();
		hql.append("from News n where n.appId= ? order by n.createTime desc");
		return super.executeHqlQuery(hql.toString(), new Object[]{appID});
	}

}
