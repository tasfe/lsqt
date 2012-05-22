package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.content.model.News;
import org.lsqt.content.model.User;


public interface NewsDao {
	
	public boolean save(News news);

	public News update(News news);

	public boolean deleteById(Serializable id);

	public News findById(Serializable id) ;
	
}
