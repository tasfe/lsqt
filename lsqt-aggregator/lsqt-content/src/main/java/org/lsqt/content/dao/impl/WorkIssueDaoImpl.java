package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.WorkIssueDao;
import org.lsqt.content.model.WorkIssue;
import org.springframework.stereotype.Repository;
@Repository
public class WorkIssueDaoImpl extends AbstractHibernateDaoSupport<WorkIssue> implements  WorkIssueDao {

}
