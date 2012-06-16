package org.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.model.WorkTask;
import org.springframework.stereotype.Repository;

@Repository
public class WorkTaskDaoImpl  extends AbstractHibernateDaoSupport<WorkTask> implements WorkTaskDao{

}
