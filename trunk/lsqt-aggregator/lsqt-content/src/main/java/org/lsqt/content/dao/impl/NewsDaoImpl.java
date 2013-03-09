package org.lsqt.content.dao.impl;

import java.util.List;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.components.dao.suport.Page;
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

	@Override
	public Page getPageByAppID(String appID, Page page){
		StringBuffer hql=new StringBuffer();
		hql.append("from News n where n.appId= ? ");
		return super.loadPageByHql(hql.toString(),new Object[]{appID},page);
	}
	
	@Override
	public Page getPageByCategoryID(String categoryID, Page page){
		StringBuffer hql=new StringBuffer();
		hql.append("select n from News n inner join n.midCateNewInfoSet  c where c.cateId= ?   ");
		return super.loadPageByHql(hql.toString(), new Object[]{categoryID}, page);
	}
}
