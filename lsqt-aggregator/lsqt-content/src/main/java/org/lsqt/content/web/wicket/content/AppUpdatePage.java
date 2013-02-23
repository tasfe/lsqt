package org.lsqt.content.web.wicket.content;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;

public class AppUpdatePage extends WebPage {
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	private Application app=new Application();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ModalWindow window;
	private  AppListPage parantPage;
	public AppUpdatePage(final PageReference modalWindowPage, final ModalWindow window,String id){
		this.window=window;
		
		this.parantPage=(AppListPage)modalWindowPage.getPage();
		
		if (StringUtils.isNotEmpty(id)) 
		{
			app = appsService.findById(id);
			
		}
		layout();
	}	
	
	private void layout(){
		
		Model<Application> appModel=new Model<Application>(app);
		Form<Application> form=new Form<Application>("form",appModel);
		
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
				
			}
		};
		
		AjaxSubmitLink btnSubmit=new AjaxSubmitLink("btnSubmit",form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				appsService.update((Application)form.getModelObject());
				window.close(target);
			}
		};
		
		add(form);
		{
			form.add(txtAppName);
			form.add(txtDesc);
			form.add(txtOrderNum);
			form.add(btnSubmit);
			form.add(btnBack);
		}
	}
}
