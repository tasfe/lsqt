package org.lsqt.content.dao.impl;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.TemplateDao;
import org.lsqt.content.model.Template;
import org.springframework.stereotype.Repository;

/**
 * 模板文件定义，持久层实现.
 * @author 袁明敏
 *
 */
@Repository
public class TemplateDaoImpl  extends AbstractHibernateDaoSupport<Template> implements TemplateDao{
	
	@Override
	public int getMaxOrderNum(String cateId){
		StringBuffer hql=new StringBuffer();
		hql.append("select max(a.orderNum) from Template a where a.cateId= ? ");
		Object rs=super.uniqueResultByHql(hql.toString(),new Object[]{cateId});
		
		return rs==null ? 0: Integer.valueOf(rs.toString());
	}

	@SuppressWarnings("unchecked")
	public Page<Template> getPageByCategoryId(String categoryId,int currPage, int perPageItems){
		StringBuffer hql=new StringBuffer();
		hql.append("from Template a where a.cateId= ? ");
		return super.loadPageByHql(hql.toString(),new Object[]{categoryId}, new Page(perPageItems,currPage));
	}
}
