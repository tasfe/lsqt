package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.AjaxLazyLoadPanel;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.MatchWay;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.content.bean.PagenationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppListPage  extends ConsoleIndex{
	private static final Logger log = LoggerFactory.getLogger(AppListPage.class);
	
	@SpringBean AppsService appsService;
	
	private PagenationBean   bean =new PagenationBean() ;
	
	private List<Application> data=new ArrayList<Application> ();
	private ListDataProvider<Application> provider=new ListDataProvider<Application>(data);
	/**  */
	private static final long serialVersionUID = 1L;

	WebMarkupContainer ctnPageBar;
	WebMarkupContainer ctnAppList;
	WebMarkupContainer ctnSearch;
	//AjaxLazyLoadPanel ctnLazy;
	public AppListPage(){
		
		Page<Application> page = new Page<Application>(20, 1);
		page.addOrderProperties("createTime", true);
		page=appsService.loadPage(page);
		refresh(page);
		
		
		ctnPageBar=(WebMarkupContainer)new WebMarkupContainer("pageBar").setOutputMarkupId(true);
		
			Label   lblTotalPage=new Label("totalPage", new PropertyModel<PagenationBean>(bean,"totalPage"));
			Label  lblTotalRecord=new Label("totalRecord", new PropertyModel<PagenationBean>(bean,"totalRecord"));
			final TextField<Integer> txtPerPageRecord=new TextField<Integer>("perPageRecord",new PropertyModel<Integer>(bean,"perPageRecord"));
			Label  lblCurrPageNum=new Label("currPageNum", new PropertyModel<PagenationBean>(bean,"currPageNum"));
			
			final TextField<Integer>  txtJumpPage=new TextField<Integer>("jumpPage", new PropertyModel<Integer>(bean,"jumpPage"));
			
			txtPerPageRecord.add(new AjaxFormComponentUpdatingBehavior("onblur") {
				
				/**	 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					bean.setPerPageRecord(txtPerPageRecord.getModel().getObject());
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum());
					initialPage=appsService.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			});
			
			AjaxLink<Void> firstPage=new AjaxLink<Void>("firstPage") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(), 1);
					initialPage=appsService.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			};	
			
			
			AjaxLink<Void> upPage=new AjaxLink<Void>("upPage") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					bean.setCurrPageNum(bean.getCurrPageNum()-1);
					
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum() );
					initialPage=appsService.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			};	
				 
			AjaxLink<Void> nextPage=new AjaxLink<Void>("nextPage") {
				@Override
				public void onClick(AjaxRequestTarget target) {
						bean.setCurrPageNum(bean.getCurrPageNum()+1);
					
						Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum());
						initialPage=appsService.loadPage(initialPage);
						refresh(initialPage);
						target.add(ctnAppList);
						target.add(ctnPageBar);
				}
			};	
			
			AjaxLink<Void> lastPage=new AjaxLink<Void>("lastPage") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getTotalPage());
					initialPage=appsService.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			};
		
			
			
		ctnAppList=(WebMarkupContainer) new WebMarkupContainer("appList").setOutputMarkupId(true);
			Form form=new Form("form");
			final CheckGroup group=new CheckGroup("group", new PropertyModel(bean, "selectedItems"));
			final CheckGroupSelector chbGroupSelector=new CheckGroupSelector("chbGroupSelector", group);
			
			
			
			
			/*
			final CheckBox chbGroupSelector=new CheckBox("chbGroupSelector",new Model(Boolean.FALSE));
			chbGroupSelector.add(new AjaxEventBehavior("onchange"){
				@Override
				protected void onEvent(AjaxRequestTarget target) {
					chbGroupSelector.setModelObject(!chbGroupSelector.getModelObject());
					target.add(ctnAppList);
				}
			});
			 */
			
			
			
			final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			
			final DataView dataView=new DataView<Application>("rows",provider) {
			
				/** */
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(final Item<Application> item) {
					item.setOutputMarkupId(true);
					
					final Check chbItem=new Check("chbItem",group);
					chbItem.setOutputMarkupId(true);
					chbItem.add(new AjaxEventBehavior("onchange") {
						@Override
						protected void onEvent(AjaxRequestTarget target) {
							System.out.println(chbItem.getModelObject());
							
						}
					});
					
					item.add(chbItem);
					/**/
					String seq=String.valueOf(item.getIndex()+1);
					Label lblIndex=new Label("seq",seq.length()<2 ? "0".concat(seq): seq );
					//AttributeModifier.append("for", value)
					item.add(lblIndex);
					
					
					Label lblAppName=new Label("appName", item.getModelObject().getName());
					item.add(lblAppName);
					
					Date dt=new Date(item.getModelObject().getCreateTime());
					Label lblCreateTime=new Label("createTime",df.format(dt));
					item.add(lblCreateTime);
					
					Label lblDesc=new Label("desc", item.getModelObject().getDescription());
					item.add(lblDesc);
					
					
					AjaxLink<Void> btnDelete=new AjaxLink<Void>("btnDelete") {
						/**  */
						private static final long serialVersionUID = 1L;

						@Override
						public void onClick(AjaxRequestTarget target) {
							
							String id=item.getModelObject().getId();
							appsService.deleteById(id);
							
							Page<Application> page = new Page<Application>(bean.getPerPageRecord(), bean.getCurrPageNum());
							page=appsService.loadPage(page);
							refresh(page);
							target.add(ctnAppList);
							target.add(ctnPageBar);
						}
					};
					item.add(btnDelete);
					
					AjaxLink<Void> btnUpdate=new AjaxLink<Void>("btnUpdate") {
						/**  */
						private static final long serialVersionUID = 1L;

						@Override
						public void onClick(AjaxRequestTarget target) {
							PageParameters params=new PageParameters();
							params.set("appId", item.getModelObject().getId());
							setResponsePage(AppUpdatePage.class, params);
						}
					};
					item.add(btnUpdate);
					
					AjaxLink<Void> btnManage=new AjaxLink<Void>("btnManage") {
						/**  */
						private static final long serialVersionUID = 1L;

						@Override
						public void onClick(AjaxRequestTarget target) {
							PageParameters params=new PageParameters();
							params.set("appId", item.getModelObject().getId());
							//setResponsePage(AppUpdatePage.class, params);
						}
					};
					item.add(btnManage);
				}
			};
		
			
/*			ctnLazy=(AjaxLazyLoadPanel) new AjaxLazyLoadPanel("lazy"){

				@Override
				public Component getLazyLoadComponent(String markupId) {
					
					
					
					return ctnAppList;
				}
				
			};	
			*/
			
		
		final ModalWindow modalWin=new ModalWindow("modalWin");
		
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
				public void onClose(AjaxRequestTarget target) {
					Page<Application> initialPage = new Page<Application>(bean
							.getPerPageRecord(), bean.getCurrPageNum());
					initialPage = appsService.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			});
		
		


		
		ctnSearch=(WebMarkupContainer) new WebMarkupContainer("search").setOutputMarkupId(true);
		
			final AutoCompleteTextField<String> txtKey=new AutoCompleteTextField<String>("key", new PropertyModel<String>(this, "key")){
				@Override
				protected Iterator<String> getChoices(String input) {
					if (Strings.isEmpty(input)) {
						List<String> emptyList = Collections.emptyList();
						return emptyList.iterator();
					}

					List<String> choices = new ArrayList<String>(10);
					Page<Application> page=new Page<Application>(bean.getPerPageRecord(), bean.getCurrPageNum());
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE));
					page=appsService.loadPage(page);
					for(Application a: page.getData()){
						choices.add(a.getName());
					}
					return choices.iterator();
				}
			};
			
			final AjaxLink<Void> btnAdd=new AjaxLink<Void>("btnAdd") {
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					modalWin.show(target);
				}
			};
			
			final AjaxLink<Void> btnSearch=new AjaxLink<Void>("btnSearch") {
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					
				}
			};
			
			final AjaxLink<Void> btnDeleteSels=new AjaxLink<Void>("btnDeleteSels") {
				/**  */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					//appsService.deleteByIds(bean.getSelectedItems().toArray(new Serializable[]{}));
					Collection c=(Collection)group.getModelObject();
					System.out.println(c);
					System.out.println(bean.getSelectedItems());
					Page page=new Page(bean.getPerPageRecord(),bean.getCurrPageNum());
					appsService.loadPage(page);
					refresh(page);
					target.add(ctnAppList);
					target.add(ctnPageBar);
				}
			};
			
			
		

		
		
		add(ctnSearch);
		{
			ctnSearch.add(txtKey);
			ctnSearch.add(btnAdd);
			ctnSearch.add(btnDeleteSels);
			ctnSearch.add(btnSearch);
		}
		
		//add(ctnLazy);
		{
			//ctnLazy.add(ctnAppList);
			{
				add(ctnAppList);
				ctnAppList.add(form);
				{
					form.add(group);
					{
						group.add(chbGroupSelector.setOutputMarkupId(true));
						group.add(dataView);
					}
				}
			}
		}
		
		add(ctnPageBar);
		{
			ctnPageBar.add(lblTotalRecord.setOutputMarkupId(true));
			ctnPageBar.add(lblTotalPage.setOutputMarkupId(true));
			ctnPageBar.add(txtPerPageRecord.setOutputMarkupId(true));
			ctnPageBar.add(lblCurrPageNum.setOutputMarkupId(true));
			ctnPageBar.add(txtJumpPage.setOutputMarkupId(true));
			ctnPageBar.add(firstPage);
			ctnPageBar.add(upPage);
			ctnPageBar.add(nextPage);
			ctnPageBar.add(lastPage);
		}
		
		add(modalWin);
	}
	
	
	
	public void refresh(Page<Application> page){
		this.data.clear();
		this.data.addAll(page.getData());
		
		
		bean.setTotalRecord(page.getTotalRecord());
		bean.setTotalPage(page.getTotalPage());
		bean.setPerPageRecord(page.getPerPageRecord());
		bean.setCurrPageNum(page.getCurrPageNum());
		bean.setJumpPage(page.getCurrPageNum());
	}
	

	private String key;

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

}
