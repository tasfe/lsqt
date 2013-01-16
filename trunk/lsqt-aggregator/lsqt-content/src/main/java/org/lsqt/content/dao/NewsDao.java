package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.News;
import org.lsqt.content.model.User;

/**
 * 
 * @author 袁明敏
 *
 */
public interface NewsDao extends EntityDao<News> {
	/**
	 * 获取某个应用下的所有新闻.
	 * @param appID 应用ID
	 * @return List 返回新闻列表
	 */
	List<News> getNewsByApp(String appID);
}
