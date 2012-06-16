package org.lsqt.content.dao;

import java.io.Serializable;

import org.lsqt.content.model.News;
import org.lsqt.content.model.Project;

public interface ProjectDao {
	
	public boolean save( Project project);

	public  Project update( Project project);

	public boolean deleteById(Serializable id);

	public  Project findById(Serializable id) ;
}
