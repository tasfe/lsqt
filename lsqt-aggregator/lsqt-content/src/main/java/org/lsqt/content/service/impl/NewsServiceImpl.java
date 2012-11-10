package org.lsqt.content.service.impl;



import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.NewsDao;
import org.lsqt.content.model.News;
import org.lsqt.content.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements NewsService{
	private NewsDao newsDao;

	@Resource
	public void setNewsDao(NewsDao newsDaoImpl) {
		this.newsDao = newsDaoImpl;
	}
	
	@Transactional(readOnly=false)
	public boolean save(News news){
		return newsDao.save(news);
	}
	
	@Transactional(readOnly=false)
	public News update(News news){
		return newsDao.update(news);
	}
	
	@Transactional(readOnly=false)
	public boolean deleteById(String id){
		return newsDao.deleteById(id);
	}
	
	public News findById(String id) {
		return newsDao.findById(id);
	}
	
	@Override
	public Page loadPage(Page initialPage) {
		return newsDao.loadPage(initialPage);
	}
}
