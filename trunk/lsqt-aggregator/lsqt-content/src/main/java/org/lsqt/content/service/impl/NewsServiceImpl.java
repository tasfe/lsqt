package org.lsqt.content.service.impl;



import java.util.List;

import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.LobContentDao;
import org.lsqt.content.dao.NewsDao;
import org.lsqt.content.model.NewsContent;
import org.lsqt.content.model.News;
import org.lsqt.content.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements NewsService{
	private NewsDao newsDao;
	private LobContentDao lobContentDao;
	
	@Resource
	public void setLobContentDao(LobContentDao lobContentDaoImpl){
		this.lobContentDao=lobContentDaoImpl;
	}
	@Resource
	public void setNewsDao(NewsDao newsDaoImpl) {
		this.newsDao = newsDaoImpl;
	}
	
	@Override
	public List<News> getNewsByApp(String appID) {
		return newsDao.getNewsByApp(appID);
	}
	
	@Transactional(readOnly=false)
	public boolean save(News news,String content){
		boolean flag= newsDao.save(news);
		
		NewsContent t=new NewsContent();
		//t.setId(news.getId());
		t.setValue(content);
		t.setNews(news);
		lobContentDao.save(t);
		
		return flag;
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
