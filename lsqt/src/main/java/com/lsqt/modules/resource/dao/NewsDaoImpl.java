package com.lsqt.modules.resource.dao;


import org.springframework.stereotype.Repository;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.modules.resource.model.News;

@Repository
public class NewsDaoImpl  extends AbstractHibernateDaoSupport<News> implements NewsDao{
	
}
