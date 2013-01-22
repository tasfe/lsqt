package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.MatchWay;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class AppListPage  extends ConsoleIndex{
	private static final Logger log = LoggerFactory.getLogger(AppListPage.class);
	
	@SpringBean AppsService appsService;
	
	/**  */
	private static final long serialVersionUID = 1L;

	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("search").setOutputMarkupId(true);
	
	final ModalWindow modalWin=new ModalWindow("modalWin");
	
	final SimpleDataView ctnAppList=new SimpleDataView("appList")
	{

		@Override
		protected void onLoadPage(Page page)
		{
			appsService.loadPage(page);
		}
		
		@Override
		protected void onClickDelete(AjaxRequestTarget target,IModel rowModel)
		{
			Application temp=(Application)rowModel.getObject();
			appsService.deleteById(temp.getId());
		}
	}
	.addHeadLabel(new String[]{"名称","序号","描述","创建时间"})
	.addHeadProp(new String[]{"name","orderNum","description","createTime"});
	
	
	public AppListPage(){
			
		/*	AjaxLazyLoadPanel ctnLazy=(AjaxLazyLoadPanel) new AjaxLazyLoadPanel("appList")
			{
				@Override
				public Component getLazyLoadComponent(String markupId) {
					return new SimpleDataView(markupId){
						@Override
						protected void loadPage(Page page)
						{
							appsService.loadPage(page);
						}
						
						@Override
						protected void onClickDelete(AjaxRequestTarget target,IModel rowModel)
						{
							 
						}
					};
				}
			};*/
 
			
		
		
		
			modalWin.setTitle("应用添加");
			modalWin.setCookieName("modalWin");
			modalWin.setPageCreator(new ModalWindow.PageCreator() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public AppAddPage createPage() {
					return new AppAddPage(AppListPage.this.getPageReference(),modalWin);
				}
			});
			
			modalWin.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
				@Override
				public boolean onCloseButtonClicked(AjaxRequestTarget target) {
	
					return true;
				}
			});
			
			modalWin.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
				@Override
			public void onClose(AjaxRequestTarget target){
					
				}
			});
		
		


		
		
		
			final AutoCompleteTextField<String> txtKey=new AutoCompleteTextField<String>("key", new PropertyModel<String>(this, "key")){
				@Override
				protected Iterator<String> getChoices(String input) {
					if (Strings.isEmpty(input)) {
						List<String> emptyList = Collections.emptyList();
						return emptyList.iterator();
					}

					List<String> choices = new ArrayList<String>(10);
					Page<Application> page=new Page<Application>(20, 1);
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE).like("description",  input, MatchWay.ANYWHERE));
					page=appsService.loadPage(page);
					for(Application a: page.getData()){
						choices.add(a.getName());
					}
					return choices.iterator();
				}
			};
			txtKey.add(new AjaxFormComponentUpdatingBehavior("onblur")
			{
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					String key=txtKey.getModelObject();
					Page page=new Page(20, 1);
					
					page.addConditions(new Condition().like("name", key,MatchWay.ANYWHERE).or("description", key));
					appsService.loadPage(page);
					ctnAppList.refresh(page);
					
					
					target.add(ctnAppList);
					
					
					setKey(txtKey.getModelObject());
					target.add(txtKey);
				}
			});
			
			final AjaxLink<Void> btnAdd=new AjaxLink<Void>("btnAdd") {
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					modalWin.show(target);
				}
			};
			
			final AjaxLink<Void> btnSearch=new AjaxLink<Void>("btnSearch") 
			{
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) 
				{
					String key=txtKey.getModelObject();
					Page page=new Page(20, 1);
					
					page.addConditions(new Condition().like("name", key,
						MatchWay.ANYWHERE).like("description", key,
						MatchWay.ANYWHERE));
					appsService.loadPage(page);
					ctnAppList.refresh(page);
					
					
					target.add(ctnAppList);
				}
			};
			

			
			
		

		
		add(ctnAppList.setOutputMarkupId(true));
		add(ctnSearch);
		{
			ctnSearch.add(txtKey.setOutputMarkupId(true));
			ctnSearch.add(btnAdd);
			ctnSearch.add(btnSearch);
		}
	}

	private String key;

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

}
