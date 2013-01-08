package org.lsqt.content.web.wicket.content;

import java.awt.print.Pageable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
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

public class AppListPage  extends ConsoleIndex{

	@SpringBean AppsService appsService;
	
	private int totalRecord;
	private int totalPage=1;
	private int perPageRecord=25;
	private int currPageNum=1;
	private String jumpPage;
	
	private Page<Application> page=new Page<Application>(perPageRecord,currPageNum) ;
	
	private List<Application> data=new ArrayList<Application>(0);
	private DataView<Application> dataView;
	private ListDataProvider<Application> dataProvide=new ListDataProvider<Application>(data);
	private  WebMarkupContainer ctnAppList;
	private WebMarkupContainer pageBar;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppListPage(){
		layout();
		
		pageNationBar();
		
	}
	
	private void pageNationBar(){
		pageBar=new WebMarkupContainer("pageBar");
		
		
		final Label lblTotalRecord=new Label("totalRecord", new PropertyModel(this, "totalRecord"));
		final Label lblTtotalPage=new Label("totalPage", new PropertyModel(this, "totalPage"));
		
		final TextField<Integer> txtPerPageRecord=new TextField<Integer>("perPageRecord", new PropertyModel(this, "perPageRecord"));
		txtPerPageRecord.add(new AjaxFormComponentUpdatingBehavior("onblur") {
			
			/**	 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				AppListPage.this.perPageRecord= txtPerPageRecord.getModelObject();
				AppListPage.this.getDataProvide();
				if(data.isEmpty()){
					AppListPage.this.currPageNum=1;
					AppListPage.this.getDataProvide();
				}
				target.add(ctnAppList);
				target.add(pageBar);
			}
		});
		
		AjaxLink<Void> firstPage=new AjaxLink<Void>("firstPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AppListPage.this.perPageRecord= txtPerPageRecord.getModelObject();
				AppListPage.this.currPageNum=1;
				AppListPage.this.getDataProvide();
				target.add(pageBar);
				target.add(ctnAppList);
			}
		};	
		
		
		AjaxLink<Void> upPage=new AjaxLink<Void>("upPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AppListPage.this.perPageRecord= txtPerPageRecord.getModelObject();
				AppListPage.this.currPageNum--;
				AppListPage.this.getDataProvide();
				target.add(pageBar);
				target.add(ctnAppList);
				/*if(AppListPage.this.currPageNum-1==0){
					//nothing
				}else{
					AppListPage.this.currPageNum=(AppListPage.this.currPageNum-1);
					AppListPage.this.getDataProvide();
					target.add(pageBar);
					target.add(ctnAppList);
				}*/
			}
		};	
			 
		AjaxLink<Void> nextPage=new AjaxLink<Void>("nextPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AppListPage.this.perPageRecord= txtPerPageRecord.getModelObject();
				AppListPage.this.getDataProvide();
				
				++AppListPage.this.currPageNum;
				
				if(AppListPage.this.currPageNum<=AppListPage.this.totalPage){
					AppListPage.this.getDataProvide();
					target.add(pageBar);
					target.add(ctnAppList);
				}else{
					AppListPage.this.currPageNum=AppListPage.this.totalPage;
					AppListPage.this.getDataProvide();
					target.add(pageBar);
					target.add(ctnAppList);
				}
			}
		};	
		
		AjaxLink<Void> lastPage=new AjaxLink<Void>("lastPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AppListPage.this.perPageRecord= txtPerPageRecord.getModelObject();
				
				AppListPage.this.currPageNum= AppListPage.this.totalPage;
				AppListPage.this.getDataProvide();
				target.add(pageBar);
				target.add(ctnAppList);
				 
			}
		};	
		
		Label lblCurrPageNum=new Label("currPageNum", new PropertyModel(this, "currPageNum"));
		
		TextField<String> txtJumpPage=new TextField<String>("jumpPage", new PropertyModel(this, "jumpPage"));
		
		add(pageBar.setOutputMarkupId(true));
		pageBar.add(lblTotalRecord.setOutputMarkupId(true));
		pageBar.add(lblTtotalPage.setOutputMarkupId(true));
		pageBar.add(txtPerPageRecord.setOutputMarkupId(true));
		pageBar.add(lblCurrPageNum.setOutputMarkupId(true));
		pageBar.add(txtJumpPage.setOutputMarkupId(true));
		pageBar.add(firstPage);
		pageBar.add(upPage);
		pageBar.add(nextPage);
		pageBar.add(lastPage);
	}
	
	
	private void getDataProvide(){
		
		page = new Page<Application>(perPageRecord, currPageNum);
		page.addOrderProperties("createTime", true);
		appsService.loadPage(page);

		this.totalPage = page.getTotalPage();
		this.totalRecord = page.getTotalRecord();
		this.perPageRecord = page.getPerPageRecord();
		this.currPageNum = page.getCurrPageNum();

		data.clear();
		data.addAll(page.getData());
		dataProvide=new ListDataProvider<Application>(data);
		
	}
	
	private void layout(){
		final WebMarkupContainer ctnSearch=new WebMarkupContainer("search");
		
		
		ctnAppList=new WebMarkupContainer("appList");
		ctnAppList.setOutputMarkupId(true);
		
		final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		getDataProvide();
		dataView=new DataView<Application>("rows", dataProvide) {
		
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
						AppListPage.this.currPageNum=1;
						AppListPage.this.getDataProvide();
						target.add(ctnAppList);
						target.add(pageBar);
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
		
		
		
		
		add(ctnAppList);
		add(ctnSearch);
		
		ctnAppList.add(dataView.setOutputMarkupId(true));
	}

	public String getJumpPage() {
		return jumpPage;
	}

	public void setJumpPage(String jumpPage) {
		this.jumpPage = jumpPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(int perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public int getCurrPageNum() {
		return currPageNum;
	}

	public void setCurrPageNum(int currPageNum) {
		this.currPageNum = currPageNum;
	}

}
