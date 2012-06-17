package org.lsqt.content.service;


import javax.annotation.Resource;

import org.lsqt.content.dao.WorkIssueDao;
import org.lsqt.content.model.WorkIssue;
import org.springframework.stereotype.Service;

@Service
public class WorkIssueServiceImpl implements WorkIssueService{
	private WorkIssueDao workIssueDao;
	@Resource
	public void setWorkIssuesDao(WorkIssueDao workIssuesDaoImpl) {
		workIssueDao = workIssuesDaoImpl;
	}
	
	public boolean save( WorkIssue  workIssue){
		return workIssueDao.save(workIssue);
	}

	public  WorkIssue update( WorkIssue  workIssue){
		return workIssueDao.update(workIssue);
	}

	public boolean deleteById(String id){
		return workIssueDao.deleteById(id);
	}

	public  WorkIssue findById(String id) {
		return workIssueDao.findById(id);
	}
	
	public void deleteAll(){
		workIssueDao.deleteAll();
	}
}
