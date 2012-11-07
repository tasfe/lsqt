package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.NewsDao;
import org.lsqt.content.model.News;
import org.lsqt.content.model.User;
import org.springframework.stereotype.Repository;


@Repository
public class NewsDaoImpl extends AbstractHibernateDaoSupport<News> implements NewsDao{
	

}
