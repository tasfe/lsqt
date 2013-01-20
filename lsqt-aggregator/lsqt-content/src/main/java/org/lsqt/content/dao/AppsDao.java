package org.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.hibernate.EntityDao;
import org.lsqt.content.model.Application;

public interface AppsDao extends EntityDao<Application>{

	public int getMaxOrderNum();
	
	public List<Application> findAll();
	
	public void deleteByIds(Serializable [] ids);
}
