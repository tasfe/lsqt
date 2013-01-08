package org.lsqt.content.service;

import java.util.List;

import org.lsqt.content.model.Category;

public interface CategoryService {
	
	public List<Category> getCategoryByApp(String appID);
	
	public boolean save(Category category);

	public Category update(Category category);

	public boolean deleteById(String id);

	public Category findById(String id) ;
	
}
