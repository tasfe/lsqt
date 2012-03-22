package com.lsqt.modules.resource.dao;

import com.lsqt.modules.resource.model.News;

public interface NewsDao {
	public boolean saveNews(News news);
	public boolean deleteById(String id);
	public News updateNews(News news);
	public News findNewsById(String id);
}
