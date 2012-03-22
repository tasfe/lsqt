package com.lsqt.modules.resource.dao;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.modules.resource.model.News;
import com.lsqt.modules.resource.model.ResourceType;

public class NewsDaoImpl  extends AbstractHibernateDaoSupport<News> implements NewsDao,ResourceType{
	@Override
	public boolean deleteById(String id) {
		return super.deleteById(id);
	}
	@Override
	public boolean saveNews(News news) {
		return super.save(news);
	}
	@Override
	public News updateNews(News news) {
		return super.update(news);
	}
	@Override
	public News findNewsById(String id){
		return super.findById(id);
	}
	

}
