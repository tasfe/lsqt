package org.lsqt.content.service;

import java.io.Serializable;

import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Category;

public interface CategoryService {
	public boolean save(Category category);

	public Category update(Category category);

	public boolean deleteById(String id);

	public Category findById(String id) ;
	
	public void deleteAll();
	
	public Category getRoot();
	
	public Page loadPage(Page initialPage) ;
	
	public boolean hasRoot();
	
}
