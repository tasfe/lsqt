package org.lsqt.content.web.wicket.content;


import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;
import org.lsqt.content.model.Category;
import org.lsqt.content.web.wicket.AbstractPage;

public class CategoryPage extends AbstractPage {
	private ValueMap valueMap=new ValueMap();
	public CategoryPage(){
		final Category category=	new Category();
		final Category parentCategory=new Category();
		
		Form<Category> form=new Form<Category>("form",new Model<Category>(category)){
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				System.out.println(category.getDescription()+"");
				System.out.println(valueMap.get("name"));
			}
		};
		add(form);
		//父类
		TextField<ValueMap> txtParentCategoryName=new RequiredTextField<ValueMap>("parentCategoryName",new PropertyModel<ValueMap>(valueMap,"name"));
		form.add(txtParentCategoryName);
		
		//类别名称
		TextField<Category> txtName=new RequiredTextField<Category>("name",new PropertyModel<Category>(category,"name"));
		form.add(txtName);
		
		//类别描述
		TextArea<Category> txtDescription=new TextArea<Category>("description", new PropertyModel<Category>(category,"description"));
		form.add(txtDescription);
	}
}
