package org.lsqt.content.web.wicket.content;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.CategoryType;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;

public class CategoryAddPage extends ConsoleIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryAddPage(){
		
		layout();

	}

	@SpringBean CategoryService categoryServ;
	private void layout() {
		final Category category=	new Category();
		final Category parentCategory=new Category();
		
		Form<Category> form=new Form<Category>("form",new Model<Category>(category)){
			private static final long serialVersionUID = 1L;

			protected void onSubmit() {
				category.setType(CategoryType.NEWS);
				
			}
		};
		
		//父类
		TextField<String> txtParentCategoryName=new RequiredTextField<String>("parentCategoryName",new PropertyModel<String>(parentCategory,"name"));
		txtParentCategoryName.setVisible(!categoryServ.hasRoot());
		
		
		//类别名称
		TextField<String> txtName=new RequiredTextField<String>("name",new PropertyModel<String>(category,"name"));
		
		//访问路径
		TextField<String> txtAccPath=new RequiredTextField<String>("accessPath",new PropertyModel<String>(category,"accessPath"));
		
		//排列顺序
		TextField<String> txtOrderNum=new RequiredTextField<String>("orderNum",new PropertyModel<String>(category,"orderNum"));
		
		//是否显示
		//CheckBox chbVisible=new CheckBox("isVisible",new PropertyModel<Boolean>(category,"isVisible"));
		RadioChoice<Boolean> radVisible=new RadioChoice<Boolean>("isVisible", Arrays.asList(new Boolean[]{Boolean.TRUE,Boolean.FALSE}),RendererUtil.getYesNoRenderer());
		radVisible.setSuffix(" &nbsp; ");
		
		//类别描述
		TextArea<Category> txtDescription=new TextArea<Category>("description", new PropertyModel<Category>(category,"description"));
		
		//返回列表
		Link<Void> btnBack=new Link<Void>("btnBack") {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(NewsMainPage.class);
			}
		};
		
		AjaxLink<Void> btnChoose=new AjaxLink<Void>("btnChoose") {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
		
				
			}
		};
		
		add(form);
		form.add(txtParentCategoryName);
		form.add(txtName);
		form.add(txtAccPath);
		form.add(txtOrderNum);
		form.add(radVisible);
		form.add(txtDescription);
		
		form.add(btnChoose);
		form.add(btnBack);
	}
}
