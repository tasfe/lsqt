package org.lsqt.content.service.impl;



import java.util.List;

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
	
	@Override
	public List<News> getNewsByApp(String appID) {
		return newsDao.getNewsByApp(appID);
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
	
	@Override
	public Page getPageByAppID(String appID, Page page){
		return newsDao.getPageByAppID(appID, page);
	}
	
	@Override
	public Page getPageByCategoryID(String categoryID, Page page)	{
		return newsDao.getPageByCategoryID(categoryID, page);
	}
}
