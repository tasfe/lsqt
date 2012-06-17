package org.lsqt.content.service;


import javax.annotation.Resource;

import org.lsqt.content.dao.ProjectDao;
import org.lsqt.content.model.Project;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl  implements ProjectService{
	
	private ProjectDao projectDao;
	@Resource
	public void setProjectDao(ProjectDao projectDaoImpl) {
		this.projectDao = projectDaoImpl;
	}
	
	public boolean save( Project project){
		return projectDao.save(project);
	}

	public  Project update( Project project){
		return projectDao.update(project);
	}

	public boolean deleteById(String id){
		return projectDao.deleteById(id);
	}

	public  Project findById(String id) {
		return projectDao.findById(id);
	}
}
