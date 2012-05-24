package org.lsqt.content.dao;

import java.io.Serializable;
import org.lsqt.content.model.Resource;

public interface ResourceDao {
	public boolean save(Resource resource);

	public Resource update(Resource resource);

	public boolean deleteById(Serializable id);

	public Resource findById(Serializable id) ;
	
	public void deleteAll();
	
	public Resource getRoot();
}
