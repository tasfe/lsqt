package com.lsqt.modules.resource.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lsqt.modules.resource.dao.NewsDao;
import com.lsqt.modules.resource.model.News;

@Service("newsService")
public class NewsServiceImpl implements NewsService{

	private NewsDao newsDao;
	@Resource
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	
	@Override
	public boolean saveNews(News news) {
		return newsDao.saveNews(news);
	}

	@Override
	public boolean deleteById(String id) {
		return newsDao.deleteById(id);
	}

	@Override
	public News updateNews(News news) {
		return newsDao.updateNews(news);
	}

	@Override
	public News findNewsById(String id) {
		return newsDao.findNewsById(id);
	}

}
