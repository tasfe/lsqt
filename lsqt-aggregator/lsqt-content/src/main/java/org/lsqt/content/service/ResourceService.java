package org.lsqt.content.service;


import org.lsqt.content.model.Resource;

public interface ResourceService {
	public boolean save(Resource resource);

	public Resource update(Resource resource);

	public boolean deleteById(String id);

	public Resource findById(String id) ;
	
	public void deleteAll();
	
	public Resource getRoot();
}
