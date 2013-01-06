package org.lsqt.content.web.wicket.content.bean;

import org.apache.wicket.model.LoadableDetachableModel;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;

public class CategoryModel extends LoadableDetachableModel<Category> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CategoryService CategorySer;
	
	private String id;
	public CategoryModel(Category c){
		id=c.getId();
	}
	@Override
	protected Category load() {
		return CategorySer.findById(id);
	}
	public void setCategorySer(CategoryService categorySer) {
		CategorySer = categorySer;
	}

}
