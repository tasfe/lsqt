package org.lsqt.content.service;


import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;

public interface NewsService {
	
	public boolean save( News  news);

	public News update(News news);

	public boolean deleteById(String id);

	public Page loadPage(Page initialPage) ;
}
