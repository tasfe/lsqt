package org.lsqt.content.dao;

import java.io.Serializable;

import org.lsqt.content.model.WorkTask;

public interface WorkTaskDao {
	
	public boolean save(WorkTask workTask);

	public WorkTask update(WorkTask workTask);

	public boolean deleteById(Serializable id);

	public WorkTask findById(Serializable id) ;
}
