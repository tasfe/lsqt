package org.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends AbstractHibernateDaoSupport<Category>  implements CategoryDao {

}
