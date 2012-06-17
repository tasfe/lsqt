package org.lsqt.content.service;


import org.lsqt.content.model.Project;

public interface ProjectService {
	
	public boolean save( Project project);

	public  Project update( Project project);

	public boolean deleteById(String id);

	public  Project findById(String id) ;
}
