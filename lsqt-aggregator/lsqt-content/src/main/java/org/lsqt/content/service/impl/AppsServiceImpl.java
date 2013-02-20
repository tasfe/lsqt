package org.lsqt.content.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.AppsDao;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppsServiceImpl implements AppsService{
	private AppsDao appsDao;

	@Resource
	public void setAppsDao(AppsDao appsDao) {
		this.appsDao = appsDao;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page loadPage(String key,Page initialPage) {
		return appsDao.loadPage(key,initialPage);
	}

	@Transactional
	@Override
	public void save(Application app) {
		appsDao.save(app);
	}
	
	@Transactional
	public void deleteById(String id){
		appsDao.deleteById(id);
	}
	
	@Transactional
	 public void deleteByIds(Serializable [] ids){
		 appsDao.deleteByIds(ids);
	 }
	
	public Application findById(String id){
		return appsDao.findById(id);
	}
	
	@Transactional
	public Application update(Application app){
		return appsDao.update(app);
	}
	
	/**
	 * 获取最大的序列号
	 * @return -
	 */
	public int getMaxOrderNum(){
		return appsDao.getMaxOrderNum();
	}
	
	public List<Application> findAll(){
		return appsDao.findAll();
	}
}
