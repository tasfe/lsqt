package org.lsqt.content.dao;

import java.io.Serializable;

import org.lsqt.content.model.WorkIssue;

public interface WorkIssueDao {
	
	public boolean save( WorkIssue  workIssue);

	public  WorkIssue update( WorkIssue  workIssue);

	public boolean deleteById(Serializable id);

	public  WorkIssue findById(Serializable id) ;
}
