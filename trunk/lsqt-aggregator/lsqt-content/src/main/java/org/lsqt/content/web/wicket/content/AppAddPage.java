package org.lsqt.content.web.wicket.content;

import javax.inject.Named;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.form.SimpleForm;

public class AppAddPage extends WebPage {
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModalWindow window;
	private  AppListPage parantPage;
	private SimpleDataView table;
	private FeedbackPanel feedBackPanel=(FeedbackPanel) new FeedbackPanel("feedBackPanel").setOutputMarkupId(true);
	
	public AppAddPage(final PageReference modalWindowPage, final ModalWindow window,final SimpleDataView table){
		layout();
		this.window=window;
		
		
		this.parantPage=(AppListPage)modalWindowPage.getPage();
		
		this.table=table;
		
		
	}
	
	private void layout(){
		Application app=new Application();
		app.setOrderNum(appsService.getMaxOrderNum()+1);
		Model<Application> appModel=new Model<Application>(app);
		final SimpleForm<Application> form=new SimpleForm<Application>("form",appModel);
		
		final TextField<String> txtAppName=new RequiredTextField<String>("appName", new PropertyModel<String>(app, "name") );
		final TextField<String> txtEngName=new RequiredTextField<String>("engName", new PropertyModel<String>(app, "engName") );
		
		final TextField<String> txtDesc=new TextField<String>("desc", new PropertyModel<String>(app, "description") );
		final TextField<String> txtOrderNum=new TextField<String>("orderNum",new PropertyModel<String>(app,"orderNum"));
		 
		form.add(new AjaxSubmitLink("btnAdd") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				Application app=(Application)form.getModelObject();
				appsService.save(app);
				System.out.println(table.getSelectedItems());
			  
				
				window.close(target);			
			}
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedBackPanel);
			}
		});
			 
		
		AjaxLink<Void> btnBack=new AjaxLink<Void>("btnCancel"){
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		};
		
		add(form);
		{
			form.add(feedBackPanel);
			form.add(txtAppName);
			form.add(txtEngName);
			form.add(txtDesc);
			form.add(txtOrderNum);
			form.add(btnBack);
		}
	}
}
