package com.lsqt.modules.resource.service;

import com.lsqt.modules.resource.model.News;

public interface NewsService {
	public boolean saveNews(News news);
	public boolean deleteById(String id);
	public News updateNews(News news);
	public News findNewsById(String id);
}
