package org.lsqt.content.web.wicket.content;


import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.lsqt.content.model.Category;
import org.lsqt.content.web.wicket.AbstractPage;

public class CategoryPage extends AbstractPage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryPage(){
		
		final Category category=	new Category();
		final Category parentCategory=new Category();
		
		Form<Category> form=new Form<Category>("form",new Model<Category>(category)){
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				System.out.println(category.getDescription()+"");
				System.out.println(parentCategory.getName());
				
			}
		};
		add(form);
		//父类
		TextField<String> txtParentCategoryName=new RequiredTextField<String>("parentCategoryName",new PropertyModel<String>(parentCategory,"name"));
		form.add(txtParentCategoryName);
		
		//类别名称
		TextField<String> txtName=new RequiredTextField<String>("name",new PropertyModel<String>(category,"name"));
		form.add(txtName);
		
		//类别类型值
		TextField<Integer> txtType=new TextField<Integer>("type",new PropertyModel<Integer>(category,"type"));
		txtType.setRequired(true);
		form.add(txtType);
		
		//类别描述
		TextArea<Category> txtDescription=new TextArea<Category>("description", new PropertyModel<Category>(category,"description"));
		form.add(txtDescription);

	}
}

class Project implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
