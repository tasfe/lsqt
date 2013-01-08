package org.lsqt.content.web.wicket.content;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;

public class AppAddPage extends ConsoleIndex {
	private @SpringBean AppsService appsService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppAddPage(){
		layout();
	}
	
	private void layout(){
		Application app=new Application();
		app.setOrderNum(appsService.getMaxOrderNum()+1);
		Model<Application> appModel=new Model<Application>(app);
		Form<Application> form=new Form<Application>("form",appModel){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				appsService.save(getModelObject());
				setResponsePage(AppListPage.class);
			}
		};
		
		TextField<String> txtAppName=new TextField<String>("appName", new PropertyModel<String>(app, "name") );
		TextField<String> txtDesc=new TextField<String>("desc", new PropertyModel<String>(app, "description") );
		TextField<String> txtOrderNum=new RequiredTextField<String>("orderNum",new PropertyModel<String>(app,"orderNum"));
		
		AjaxLink<Void> btnBack=new AjaxLink<Void>("btnBack"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AppListPage.class);
			}
		};
		
		add(form);
		form.add(txtAppName);
		form.add(txtDesc);
		form.add(txtOrderNum);
		form.add(btnBack);
	}
}
