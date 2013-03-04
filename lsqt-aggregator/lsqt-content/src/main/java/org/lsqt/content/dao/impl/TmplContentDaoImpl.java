package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.TmplContentDao;
import org.lsqt.content.model.TmplContent;
import org.springframework.stereotype.Repository;


@Repository
public class TmplContentDaoImpl  extends AbstractHibernateDaoSupport<TmplContent> implements TmplContentDao{

}
