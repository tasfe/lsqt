package org.lsqt.content.web.wicket.content;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;

public class AppAddPage extends WebPage {
	private @SpringBean AppsService appsService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModalWindow window;
	private  AppAddPage parantPage;
	public AppAddPage(final PageReference modalWindowPage, final ModalWindow window){
		layout();
		this.window=window;
		
		
		this.parantPage=(AppAddPage)modalWindowPage.getPage();
	}
	
	private void layout(){
		Application app=new Application();
		app.setOrderNum(appsService.getMaxOrderNum()+1);
		Model<Application> appModel=new Model<Application>(app);
		final Form<Application> form=new Form<Application>("form",appModel);
		
		final TextField<String> txtAppName=new TextField<String>("appName", new PropertyModel<String>(app, "name") );
		final TextField<String> txtDesc=new TextField<String>("desc", new PropertyModel<String>(app, "description") );
		final TextField<String> txtOrderNum=new RequiredTextField<String>("orderNum",new PropertyModel<String>(app,"orderNum"));
		 
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
				window.close(target);				
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
		form.add(txtAppName);
		form.add(txtDesc);
		form.add(txtOrderNum);
		form.add(btnBack);
	}
}
