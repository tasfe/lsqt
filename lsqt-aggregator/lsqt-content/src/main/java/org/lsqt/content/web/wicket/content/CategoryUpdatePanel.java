package org.lsqt.content.web.wicket.content;

import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.component.form.SimpleForm;
import org.lsqt.content.web.wicket.util.RendererUtil;

public class CategoryUpdatePanel extends Panel
{
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@SuppressWarnings("serial")
	public CategoryUpdatePanel(String id,String categoryID){
		super(id);

		FeedbackPanel feedbackPanel=new FeedbackPanel("feedbackPanel");
		feedbackPanel.setMaxMessages(1);
		feedbackPanel.setOutputMarkupPlaceholderTag(true);
		
		Category category=categoryServ.findById(categoryID);
		final SimpleForm<Category> form = new SimpleForm<Category>("form", 	new Model<Category>(category));
		// 类别名称
		TextField<String> txtName = new RequiredTextField<String>("name", new PropertyModel<String>(category, "name"));

		// 栏目英文名称
		TextField<String> txtEngName = new RequiredTextField<String>("engName", new PropertyModel<String>(category, "engName"));
				
		// 排列顺序
		TextField<String> txtOrderNum = new RequiredTextField<String>( "orderNum", new PropertyModel<String>(category, "orderNum"));

		
		RadioChoice<Boolean> radVisible = new RadioChoice<Boolean>("isVisible", new PropertyModel(category,"isVisible"), Arrays.asList(new Boolean[]{Boolean.TRUE, Boolean.FALSE}), RendererUtil.getYesNoRenderer());
		radVisible.setSuffix(" &nbsp; ");

		// 类别描述
		TextArea<Category> txtDescription = new TextArea<Category>( "description", new PropertyModel<Category>(category, "description"));

		AjaxSubmitLink btnAdd = new AjaxSubmitLink("btnAdd", form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{

				Category pojo = (Category) form.getModelObject();
				categoryServ.update(pojo);
				onUpdateAfter(target);
			}
		};

		// 取消
		AjaxLink<Void> btnCancel = new AjaxLink<Void>("btnCancel")
		{
			/**  */
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				onCancelAfter(target);
			}
		};

		add(form);
		{
			form.add(feedbackPanel);
			form.add(txtName);
			form.add(txtEngName);
			form.add(txtOrderNum);
			form.add(radVisible);
			form.add(txtDescription);
			
			form.add(btnCancel);
			form.add(btnAdd);
		}
	}
	
	protected void onUpdateAfter(AjaxRequestTarget target){
		
	}
	
	protected void onCancelAfter(AjaxRequestTarget target){
		
	}
}
