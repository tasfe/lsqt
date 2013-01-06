package org.lsqt.content.web.wicket.content.bean;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;

public class CategoryProvider implements ITreeProvider<Category> {
	private CategoryService CategorySer;
	public void setCategorySer(CategoryService categorySer) {
		CategorySer = categorySer;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void detach() {
		
		
	}

	@Override
	public Iterator<? extends Category> getRoots() {
		
		return null;
	}

	@Override
	public boolean hasChildren(Category node) {
		
		return false;
	}

	@Override
	public Iterator<? extends Category> getChildren(Category node) {
		
		return null;
	}

	@Override
	public IModel<Category> model(Category object) {
		CategoryModel c=	 new CategoryModel(object);
		c.setCategorySer(CategorySer);
		return c;
	}

}
