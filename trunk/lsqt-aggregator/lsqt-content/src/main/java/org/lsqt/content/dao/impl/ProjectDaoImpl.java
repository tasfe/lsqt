package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.ProjectDao;
import org.lsqt.content.model.Project;
import org.springframework.stereotype.Repository;
@Repository
public class ProjectDaoImpl  extends AbstractHibernateDaoSupport<Project> implements ProjectDao{

}
