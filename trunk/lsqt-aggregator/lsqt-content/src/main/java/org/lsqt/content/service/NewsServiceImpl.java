package org.lsqt.content.service;


import java.io.Serializable;
import java.util.Date;

import javax.annotation.Resource;

import org.lsqt.content.dao.NewsDao;
import org.lsqt.content.model.News;
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
	public boolean save(News news2){
		for(int i=0;i<100;i++){
			News news=new News();
			news.setContent("content");
			news.setContentKeys("java");
			news.setCreateTime(System.currentTimeMillis());
			news.setDescription("desc");
			news.setIsEnable(true);
			news.setIsPublished(true);
			news.setName("xxxx");
			news.setOnlineTime(new Date());
			news.setPubTime(new Date());
			news.setSourceFrom("http://sohu.com");
			news.setTitle("title");
			newsDao.save(news);
			if(i==20){
			//	throw new RuntimeException("xxxxxxxxxxxxxxxxx");
			}
		}
		
		return true;
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


}
