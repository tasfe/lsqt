package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.dao.LobContentDao;
import org.lsqt.content.model.NewsContent;
import org.springframework.stereotype.Repository;

@Repository
public class LobContentDaoImpl extends AbstractHibernateDaoSupport<NewsContent>  implements LobContentDao{

}
