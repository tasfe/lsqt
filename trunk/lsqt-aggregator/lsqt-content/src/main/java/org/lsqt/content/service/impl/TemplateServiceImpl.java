package org.lsqt.content.service.impl;


import java.io.File;

import javax.annotation.Resource;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.dao.TemplateDao;
import org.lsqt.content.dao.TmplContentDao;
import org.lsqt.content.model.Template;
import org.lsqt.content.model.TmplContent;
import org.lsqt.content.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 * 模板基本信息与模板内容操作定义.
 * @author 袁明敏
 *
 */
@Service
public class TemplateServiceImpl implements TemplateService{
	
	
	@Override
	public int getMaxOrderNum(String cateId){
		return templateDao.getMaxOrderNum(cateId);
	}
	
	@Override
	@Transactional
	public void save(Template template, TmplContent content){
		templateDao.save(template);
		content.setTemplate(template);
		tmplContentDao.save(content);
		
	}
	
	@Transactional
	public void deleteById(String id){
		Template templ=templateDao.findById(id);
		if(templ.getDiskPath()!=null){
			File file=new File (templ.getDiskPath());
			if(file.exists()){
				file.delete();
			}
		}
		templateDao.deleteById(id);
	}
	
	@Override
	public Template findById(String id){
		return templateDao.findById(id);
	}
	
	@Override
	public Page<Template> getPageByCategoryId(String categoryId,int currPage,int perPageItems){
		return templateDao.getPageByCategoryId(categoryId, currPage, perPageItems);
	}
	
	private TemplateDao templateDao;
	private TmplContentDao tmplContentDao;
	@Autowired
	public void setTemplateDao(@Qualifier("templateDaoImpl") TemplateDao  templateDao){
		this.templateDao = templateDao;
	}
	
	@Resource
	public void setTmplContentDao(TmplContentDao tmplContentDao){
		this.tmplContentDao = tmplContentDao;
	}
	
	
}
