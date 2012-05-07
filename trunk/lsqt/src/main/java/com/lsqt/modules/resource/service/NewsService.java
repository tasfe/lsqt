package com.lsqt.modules.resource.service;

import java.util.List;

import com.lsqt.modules.resource.model.News;

public interface NewsService {
	public boolean save(News news);
	public News update(News news);
	public boolean deleteById(String id);
	public News findById(String id) ;
	public List<News> findAll();
}
