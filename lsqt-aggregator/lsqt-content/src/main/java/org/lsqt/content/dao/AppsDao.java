package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;

public interface AppsDao extends EntityDao<Application>{

	public int getMaxOrderNum();
	
	public List<Application> findAll();
	
	public void deleteByIds(Serializable [] ids);
	
	public Page<Application> loadPage(String key,Page page);
	
}
