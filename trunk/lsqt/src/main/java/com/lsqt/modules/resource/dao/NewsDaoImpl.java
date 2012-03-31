package com.lsqt.modules.resource.dao;

import org.springframework.stereotype.Repository;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.modules.resource.model.News;
import com.lsqt.modules.resource.model.ResourceType;
@Repository
public class NewsDaoImpl  extends AbstractHibernateDaoSupport<News> implements NewsDao{
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
