package org.lsqt.content.service;


import javax.annotation.Resource;
import org.lsqt.content.dao.ResourceDao;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService{
	private ResourceDao resourceDao;
	@Resource
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	public boolean save(org.lsqt.content.model.Resource resource){
		return this.resourceDao.save(resource);
	}

	public org.lsqt.content.model.Resource update(org.lsqt.content.model.Resource resource){
		return this.resourceDao.update(resource);
	}

	public boolean deleteById(String id){
		return this.resourceDao.deleteById(id);
	}

	public org.lsqt.content.model.Resource findById(String id) {
		return this.resourceDao.findById(id);
	}
	
	public void deleteAll(){
		this.resourceDao.deleteAll();
	}
	
	public org.lsqt.content.model.Resource getRoot(){
		return this.resourceDao.getRoot();
	}

	
}
