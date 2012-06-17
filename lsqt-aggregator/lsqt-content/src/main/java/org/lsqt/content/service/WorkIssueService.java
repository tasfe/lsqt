package org.lsqt.content.service;


import org.lsqt.content.model.WorkIssue;

public interface WorkIssueService {
	
	public boolean save( WorkIssue  workIssue);

	public  WorkIssue update( WorkIssue  workIssue);

	public boolean deleteById(String id);

	public  WorkIssue findById(String id) ;
	
	public void deleteAll();
}
