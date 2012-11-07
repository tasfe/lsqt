package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.WorkTaskDao;
import org.lsqt.content.model.WorkTask;
import org.springframework.stereotype.Repository;

@Repository
public class WorkTaskDaoImpl  extends AbstractHibernateDaoSupport<WorkTask> implements WorkTaskDao{

}
