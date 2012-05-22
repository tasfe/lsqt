package org.lsqt.content.service;


import org.lsqt.content.model.News;

public interface NewsService {
	public boolean save(News news);
	public News update(News news);
	public boolean deleteById(String id);
	public News findById(String id) ;
}
