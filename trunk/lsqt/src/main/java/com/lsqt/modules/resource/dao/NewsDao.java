package com.lsqt.modules.resource.dao;

import java.io.Serializable;
import java.util.List;

import com.lsqt.modules.resource.model.News;

public interface NewsDao {
	public boolean save(News news);
	public News update(News news);
	public boolean deleteById(Serializable id);
	public News findById(Serializable id) ;
	public List<News> findAll();
}
