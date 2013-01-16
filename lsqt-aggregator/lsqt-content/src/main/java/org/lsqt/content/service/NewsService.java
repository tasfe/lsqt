package org.lsqt.content.service;


import java.util.List;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;

public interface NewsService {
	/**
	 * 获取某个应用下的所有新闻.
	 * @param appID 应用ID
	 * @return List 返回新闻列表
	 */
	public List<News> getNewsByApp(String appID);
	
	public boolean save( News  news);

	public News update(News news);

	public boolean deleteById(String id);

	public Page loadPage(Page initialPage) ;
}
