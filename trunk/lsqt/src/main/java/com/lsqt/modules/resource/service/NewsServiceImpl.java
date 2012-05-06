package com.lsqt.modules.resource.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsqt.modules.resource.dao.NewsDao;
import com.lsqt.modules.resource.model.News;

@Service("newsService")
public class NewsServiceImpl implements NewsService{

	private NewsDao newsDao;
	@Resource
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	@Transactional(readOnly=false)
	public boolean save(News news){
		return this.newsDao.save(news);
	}
	
	@Transactional(readOnly=false)
	public News update(News news){
		return this.newsDao.update(news);
	}
	
	@Transactional(readOnly=false)
	public boolean deleteById(String id){
		return this.newsDao.deleteById(id);
	}
	
	public News findById(String id) {
		return this.newsDao.findById(id);
	}
	
	public List<News> findAll(){
		return this.newsDao.findAll();
	}

}
