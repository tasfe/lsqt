package org.lsqt.content.service;


import org.lsqt.content.model.WorkTask;

public interface WorkTaskService {
	
	public boolean save(WorkTask workTask);

	public WorkTask update(WorkTask workTask);

	public boolean deleteById(String id);

	public WorkTask findById(String id) ;
	
	public void deleteAll();
}
