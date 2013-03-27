package org.lsqt.content.web.wicket.content;

import java.io.Serializable;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
	
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	/**  */
	private static final long serialVersionUID = 1L;

	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("search").setOutputMarkupId(true);
	
	final SimpleDataView ctnAppList=(SimpleDataView) new SimpleDataView("appList")
	{

		@Override
		protected void onLoadPage(Page page)
		{
			appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
		}
		
		@Override
		protected void onClickDelete(AjaxRequestTarget target,IModel rowModel)
		{
			Application temp=(Application)rowModel.getObject();
			appsService.deleteById(temp.getId());

			Page page=new Page(getPerPageRecord(),getCurrPage());
			
			appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
			refresh(page);
			target.add(ctnAppList);
			
		}
		
		protected void onClickUpdate(AjaxRequestTarget target, final org.apache.wicket.model.IModel<Object> rowModel) 
		{
			final ModalWindow window=getModalWindow();
			window.setTitle("更新应用");
			window.setInitialWidth(400);
			window.setInitialHeight(180);
			window.setPageCreator(new ModalWindow.PageCreator()
			{
				public AppUpdatePage createPage() 
				{
					Application entity=(Application)rowModel.getObject();
					return new AppUpdatePage(getPageReference(),window,entity.getId());
				}
			});
			window.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
			{
				
				@Override
				public void onClose(AjaxRequestTarget target)
				{
					refreshPage();
					target.add(ctnAppList);
				}
			});
			window.show(target);
			
			
		};
	}
	.addHeadLabel(new String[]{"名称","序号","描述","创建时间"})
	.addHeadProp(new String[]{"name","orderNum","description","createTime"})
	.setVisibleForCreateButton(false)
	.setOutputMarkupId(true);
	
	
	
	private void refreshPage()
	{
		int perPageRecord=ctnAppList.getPerPageRecord();
		int currPageNum=ctnAppList.getCurrPage();
		Page page=new Page(perPageRecord,currPageNum);
		appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
		ctnAppList.refresh(page);
		
	}
	
	public AppListPage(){
		Form form=new Form("form");
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
 
			
		
		
		
			
		


			final AutoCompleteTextField<String> txtKey=new AutoCompleteTextField<String>("key", new PropertyModel<String>(this, "key")){
				
				@Override
				protected Iterator<String> getChoices(String input)
				{
					if (Strings.isEmpty(input))
					{
						List<String> emptyList = Collections.emptyList();
						return emptyList.iterator();
					}
					
					Page page = new Page(18, 1);
					appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
					
					Set<String> set=new LinkedHashSet<String>();
					for(Application a:  appsService.loadPage(input, page).getData()){
						set.add(a.getName());
					}
					return set.iterator();
				}

			};
			//txtKey.add(AttributeModifier.append("style","background:lightgreen;border-color:green"));
		
			/*;*/
			txtKey.add(new AjaxFormComponentUpdatingBehavior("onchange")
			{
				
				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					setKey(txtKey.getModelObject());
					target.add(txtKey);
				}
			});
			
			final AjaxLink<Void> btnAdd=new AjaxLink<Void>("btnAdd") {
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					final ModalWindow modalWin=ctnAppList.getModalWindow();
					modalWin.setTitle("应用添加");
					modalWin.setInitialWidth(350);
					modalWin.setInitialHeight(180);
					modalWin.setPageCreator(new ModalWindow.PageCreator()
					{
						public AppAddPage createPage() 
						{
							return new AppAddPage(getPageReference(),modalWin,ctnAppList);
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
							refreshPage();
							
							target.add(ctnAppList);
						}

						
					});
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
					
						String key = txtKey.getModelObject();
						key=getKey();
						if (StringUtils.isNotEmpty(key))
						{
							Page page = new Page(18, 1);
		
							appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
							ctnAppList.refresh(page);
							
							target.add(ctnAppList);
						}else{
							Page page = new Page(18, 1);
							
							appsService.loadPage(key == null ? StringUtils.EMPTY : key, page);
							ctnAppList.refresh(page);
							
							target.add(ctnAppList);
						}
				}
			};
			
			final AjaxSubmitLink btnDeleteSels=new  AjaxSubmitLink("btnDeleteSels") 
			{
				/**  */
				private static final long serialVersionUID = 1L;
			
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					String [] ids=new String [ctnAppList.getSelectedItems().size()]; 
					for(int i=0;i<ctnAppList.getSelectedItems().size();i++)
					{
						Application apps=(Application) ctnAppList.getSelectedItems().get(i);
						ids[i]= apps.getId();
					}
					appsService.deleteByIds(ids);
					
					refreshPage();
					target.add(ctnAppList);
				}
			};
			
			final AjaxLink<Void> btnShowAll=new AjaxLink<Void>("btnShowAll")
			{
				@Override
				public void onClick(AjaxRequestTarget target)
				{
					key=StringUtils.EMPTY;
					refreshPage();
					target.add(ctnAppList);
					target.add(ctnSearch);
				}
			};
		
		
		add(form);
		{
			form.add(ctnSearch);
			form.add(ctnAppList.setOutputMarkupId(true));
			form.add(ctnSearch);
			{
				ctnSearch.add(txtKey.setOutputMarkupId(true));
				ctnSearch.add(btnAdd);
				ctnSearch.add(btnSearch);
				ctnSearch.add(btnDeleteSels);
				ctnSearch.add(btnShowAll);
			}
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
