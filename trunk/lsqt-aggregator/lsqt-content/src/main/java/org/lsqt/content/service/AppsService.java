package org.lsqt.content.service;

import java.util.List;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;

public interface AppsService {
	public List<Application> findAll();
	
	public int getMaxOrderNum();
	
	public Application update(Application app);
	
	public Application findById(String id);
	
	public void deleteById(String id);
	
	public void save(Application app);
	
	public Page loadPage(Page initialPage) ;
}
