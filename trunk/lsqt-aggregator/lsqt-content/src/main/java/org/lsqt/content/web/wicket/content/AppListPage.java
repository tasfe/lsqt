package org.lsqt.content.web.wicket.content;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.content.bean.PagenationBean;

public class AppListPage  extends ConsoleIndex{

	@SpringBean AppsService appsService;
	
	private PagenationBean   bean =new PagenationBean() ;
	
	
	private  WebMarkupContainer ctnAppList;
	private WebMarkupContainer ctnSearch;
	private WebMarkupContainer pageBar;
	
	private List<Application> data=new ArrayList<Application> ();
	private ListDataProvider<Application> provider=new ListDataProvider<Application>(data);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppListPage(){
		layout();
		
		pageNationBar();
		
		addModalWin();
	}
	
	ModalWindow modalWin;
	private void addModalWin(){
		modalWin=new ModalWindow("modalWin");
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
		
		add(modalWin);
	}
	
	
	private void pageNationBar(){
		pageBar=new WebMarkupContainer("pageBar");
		
		
		final Label lblTotalPage=new Label("totalPage", new PropertyModel<PagenationBean>(bean,"totalPage"));
		final Label lblTotalRecord=new Label("totalRecord", new PropertyModel<PagenationBean>(bean,"totalRecord"));
		final TextField<Integer> txtPerPageRecord=new TextField<Integer>("perPageRecord",new PropertyModel<Integer>(bean,"perPageRecord"));
		txtPerPageRecord.add(new AjaxFormComponentUpdatingBehavior("onblur") {
			
			/**	 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				bean.setPerPageRecord(txtPerPageRecord.getModel().getObject());
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum());
				appsService.loadPage(initialPage);
				refresh(initialPage.getData(), initialPage, bean.getCurrPageNum());
				target.add(ctnAppList);
			}
		});
		
		AjaxLink<Void> firstPage=new AjaxLink<Void>("firstPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(), 1);
				appsService.loadPage(initialPage);
				refresh(initialPage.getData(), initialPage, 1);
				target.add(ctnAppList);
			}
		};	
		
		
		AjaxLink<Void> upPage=new AjaxLink<Void>("upPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				bean.setCurrPageNum(bean.getCurrPageNum()-1);
				
				if(bean.getCurrPageNum()>0){
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum() );
					appsService.loadPage(initialPage);
					refresh(initialPage.getData(), initialPage, initialPage.getCurrPageNum());
					target.add(ctnAppList);
				}
			}
		};	
			 
		AjaxLink<Void> nextPage=new AjaxLink<Void>("nextPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				
				if(bean.getCurrPageNum()<bean.getTotalPage()){
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum()+1);
					appsService.loadPage(initialPage);
					refresh(initialPage.getData(), initialPage, initialPage.getCurrPageNum());
					target.add(ctnAppList);
				}
			}
		};	
		
		AjaxLink<Void> lastPage=new AjaxLink<Void>("lastPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				 
				target.add(ctnAppList);
			}
		};
		
		Label lblCurrPageNum=new Label("currPageNum", new PropertyModel<PagenationBean>(bean,"currPageNum"));
		
		TextField txtJumpPage=new TextField("jumpPage", new PropertyModel<PagenationBean>(bean,"jumpPage"));
		
		add(pageBar.setOutputMarkupId(true));
		pageBar.add(lblTotalRecord.setOutputMarkupId(true));
		pageBar.add(lblTotalPage.setOutputMarkupId(true));
		pageBar.add(txtPerPageRecord.setOutputMarkupId(true));
		pageBar.add(lblCurrPageNum.setOutputMarkupId(true));
		pageBar.add(txtJumpPage.setOutputMarkupId(true));
		pageBar.add(firstPage);
		pageBar.add(upPage);
		pageBar.add(nextPage);
		pageBar.add(lastPage);
	}
	
	private void refresh(Collection<Application> data,Page<Application> page,int jumpPage){
		this.data.clear();
		if(data!=null){
			this.data.addAll(data);
		}
		
		bean.setTotalRecord(page.getTotalRecord());
		bean.setTotalPage(page.getTotalPage());
		bean.setPerPageRecord(page.getPerPageRecord());
		bean.setCurrPageNum(page.getCurrPageNum());
		bean.setJumpPage(String.valueOf(jumpPage));
	}
	
	
	private void layout(){
		ctnSearch=new WebMarkupContainer("search");
		final TextField<String> txtKey=new TextField<String>("key", new PropertyModel<String>(this, "key"));
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
		ctnSearch.add(txtKey);
		ctnSearch.add(btnAdd);
		ctnSearch.add(btnSearch);
		
		
		
		
		
		ctnAppList=new WebMarkupContainer("appList");
		ctnAppList.setOutputMarkupId(true);
		
		final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		Page<Application> page = new Page<Application>(20, 1);
		page.addOrderProperties("createTime", true);
		appsService.loadPage(page);
		refresh(page.getData(), page, 1);
		
	
		DataView<Application> dataView=new DataView<Application>("rows",provider) {
		
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Application> item) {
				item.setOutputMarkupId(true);
				
				String seq=String.valueOf(item.getIndex()+1);
				Label lblIndex=new Label("seq",seq.length()<2 ? "0".concat(seq): seq );
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
						
						Page<Application> page = new Page<Application>(20, 1);
						appsService.loadPage(page);
						data.clear();
						data.addAll(page.getData());
						target.add(ctnAppList);
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
				
			//	AjaxPagingNavigation navigation=new 
				
				
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
		
		
		
		add(ctnAppList);
		add(ctnSearch);
		
		ctnAppList.add(dataView.setOutputMarkupId(true));
	}

	private String key;

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

}
