package org.lsqt.content.service;


import java.util.List;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;

public interface NewsService {
	/**
	 * 获取栏目下的内容.
	 * @param categoryID 栏目ID
	 * @param page 初使化分页
	 * @return 返回内容列表分页
	 */
	public Page getPageByCategoryID(String categoryID,Page page);
	
	/**
	 * 获取某个应用下的所有新闻分页.
	 * @param appID 应用id
	 * @return 返回内容列表分页
	 */
	public Page getPageByAppID(String appID,Page page);
	
	/**
	 * 获取某个应用下的所有新闻.
	 * @param appID 应用ID
	 * @return List 返回新闻列表
	 */
	public List<News> getNewsByApp(String appID);
	
	/**
	 * 保存一条新闻,以及新的内容和新闻所属的栏目
	 * @param news 新闻基本信息
	 * @param content 新闻内容
	 * @param cate 新闻所属的栏目
	 * @return boolean
	 */
	public boolean save(News news,String content,Category cate);

	public News update(News news);

	public boolean deleteById(String id);

	public Page loadPage(Page initialPage) ;
}
