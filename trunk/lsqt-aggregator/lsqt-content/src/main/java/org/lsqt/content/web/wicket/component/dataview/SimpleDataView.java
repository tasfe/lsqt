package org.lsqt.content.web.wicket.component.dataview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.lsqt.components.dao.suport.BeanHelper;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
/**
 * 表格分页控件,该控件要放在form表单内才支持全选.
 * 如: 
 * <pre>
 * Form form=new Form("form");
 * form.add(simpleDataView);
 * </pre>
 * @author 袁明敏
 *
 */
public class SimpleDataView extends Panel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private PagenationBean bean=new PagenationBean();
	
	
	private List<String> headerData=new ArrayList<String>();
	private List<String> headerProperty=new ArrayList<String>();
	
	private List<Object> bodyerData=new ArrayList<Object>();
	
	
	// because the modal open to others,so ModalWindow#setOutputMarkupPlaceholderTag(true). 
	final ModalWindow modalWindow=(ModalWindow) new ModalWindow("modalWin").setOutputMarkupPlaceholderTag(true); 
	final WebMarkupContainer ctnList=(WebMarkupContainer) new WebMarkupContainer("ctnList").setOutputMarkupPlaceholderTag(true);
	final WebMarkupContainer ctnPageBar=(WebMarkupContainer)new WebMarkupContainer("pageBar").setOutputMarkupPlaceholderTag(true);
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////
	private final  List<Object> selectedItems=new ArrayList<Object>();
	public List<Object> getSelectedItems()
	{
		return this.selectedItems;
	}

	public void setSelectedItems(List<Object> selectedItems)
	{
		this.selectedItems.clear();
		this.selectedItems.addAll(selectedItems);
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	boolean  isVisibleForCreate=true;
	public SimpleDataView setVisibleForCreateButton(boolean isVisible)
	{
		isVisibleForCreate=isVisible;
		return this;
	}
	
	boolean isVisibleForUpdate=true;
	public SimpleDataView setVisibleForUpdateButton(boolean isVisible)
	{
		isVisibleForUpdate=isVisible;
		return this;
	}
	
	boolean isVisibleForDelete=true;
	public SimpleDataView setVisibleForDeleteButton(boolean isVisible)
	{
		isVisibleForDelete=isVisible;
		return this;
	}
	
	public SimpleDataView(String id){
		
		super(id);
		
		Page temp=new Page(20,1);
		onLoadPage(temp);
		refresh(temp);
		
		
		
		//表格头部制造
		ListView<String> header = new ListView<String>("header", headerData)
		{
			@Override
			protected void populateItem(ListItem<String> item)
			{
				WebMarkupContainer headCell = new WebMarkupContainer("headCell");
				Label headerCellText =new Label("headerCellText",item.getModelObject()==null ? "": item.getModelObject().toString());
				
				headCell.add(headerCellText);
				item.add(headCell);
			}
		};
		
		final CheckGroup<Object> group=new CheckGroup<Object>("group",selectedItems);
		final	CheckGroupSelector groupSelector=new CheckGroupSelector("groupSelector", group);
		/*
		groupSelector.add(new  AjaxFormChoiceComponentUpdatingBehavior(){

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				
				
			}
			
		});
		*/
		ctnList.add(group);
		
		
		
		//表格身体制造
		ListView<Object> bodyer = new ListView<Object>("bodyer", bodyerData)
		{
			@Override
			protected void populateItem(final ListItem<Object> item)
			{
				
				final Check itemCheck=(Check)new Check("itemCheck",item.getModel(),group).setOutputMarkupId(true);
				/*
				itemCheck.add(new AjaxEventBehavior("onclick")
				{
					@Override
					protected void onEvent(AjaxRequestTarget target)
					{
						Object o=itemCheck.getModelObject();
						
						//target.appendJavaScript("$(function(){$('#"+itemCheck.getMarkupId()+"')[0].checked='';})");
						System.out.println(o);
					}
				});
				*/
				item.add(itemCheck);
				
				
				ListView<Object> row = null;
				if (item.getModelObject().getClass().isArray() == false)
				{
					List list = new ArrayList();
					for (String p : SimpleDataView.this.headerProperty)
					{
						list.add(BeanHelper.forceGetProperty( item.getModelObject(), p));
					}
					row = new ListView<Object>("row", list)
					{
						@Override
						protected void populateItem(ListItem<Object> item)
						{
							WebMarkupContainer rowCell = new WebMarkupContainer("rowCell");
							try
							{
								Label headerCellText = new Label("headerCellText",item.getModelObject()==null ? "":item.getModelObject().toString() );
								rowCell.add(headerCellText);
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						 
							item.add(rowCell);
						}
					};
				} else { // 加载数组
					Object[] temp=(Object[])item.getModelObject();
					List list=Arrays.asList(temp);
					row=new ListView<Object>("row",list)
					{
						@Override
						protected void populateItem(ListItem<Object> item)
						{
							WebMarkupContainer rowCell = new WebMarkupContainer("rowCell");
							
							Label headerCellText = new Label( 	"headerCellText",item.getModelObject()==null ? "":item.getModelObject().toString());
							rowCell.add(headerCellText);
						 
							item.add(rowCell);
						}
					};
				}

				final WebMarkupContainer operats=(WebMarkupContainer) new WebMarkupContainer("operats").setOutputMarkupPlaceholderTag(true);
				AjaxLink<Void> btnDelete= new AjaxLink<Void>("btnDelete")
				{
					@Override
					public void onClick(AjaxRequestTarget target)
					{
						onClickDelete(  target,  item.getModel());
					}
				};
				
				
			
				
				AjaxLink<Void> btnUpdate= new AjaxLink<Void>("btnUpdate")
				{
					@Override
					public void onClick(AjaxRequestTarget target)
					{
						onClickUpdate(target, item.getModel());
					}
				};
				
				
				
				AjaxLink<Void> btnCreate= new AjaxLink<Void>("btnCreate")
				{
					@Override
					public void onClick(AjaxRequestTarget target)
					{
						onClickCreate(target, item.getModel());
					}
				};
				
				
				//设置ajax占位
				if(isVisibleForDelete==false)
				{
					btnDelete.setOutputMarkupPlaceholderTag(true);
				}
				btnDelete.setVisible(isVisibleForDelete);
				
				if(isVisibleForUpdate==false)
				{
					btnUpdate.setOutputMarkupPlaceholderTag(true);
				}
				btnUpdate.setVisible(isVisibleForUpdate);
				
				if(isVisibleForCreate==false)
				{
					btnCreate.setOutputMarkupPlaceholderTag(true);
				}
				btnCreate.setVisible(isVisibleForCreate);
				
				
				
				
				
				item.add(row);
				item.add(operats);
				{
					operats.add(btnDelete);
					operats.add(btnCreate);
					operats.add(btnUpdate);
				}
			}
		}; //.setReuseItems(true)
		
		/*CheckGroup group=new CheckGroup("btnSelectAll", new PropertyModel(this, "selectedItems"));
		CheckGroupSelector selector=new CheckGroupSelector("btnSelectAll",group);*/
		
	/*	final CheckBox btnSelectAll=new CheckBox("btnSelectAll",new Model(Boolean.FALSE));
		btnSelectAll.add(new AjaxEventBehavior("click")
		{
			
			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				btnSelectAll.setModelObject(!btnSelectAll.getModelObject() );
				System.out.println(btnSelectAll.getModelObject());
				target.add(btnSelectAll);
				
			}
		});*/
	 
		
		final Label   lblTotalPage=new Label("totalPage", new PropertyModel<PagenationBean>(bean,"totalPage"));
		final Label  lblTotalRecord=new Label("totalRecord", new PropertyModel<PagenationBean>(bean,"totalRecord"));
		final TextField<Integer> txtPerPageRecord=new TextField<Integer>("perPageRecord",new PropertyModel<Integer>(bean,"perPageRecord"));
		final Label  lblCurrPageNum=new Label("currPageNum", new PropertyModel<PagenationBean>(bean,"currPageNum"));
		
		final TextField<Integer>  txtJumpPage=new TextField<Integer>("jumpPage", new PropertyModel<Integer>(bean,"jumpPage"));
		txtJumpPage.add(new AjaxFormComponentUpdatingBehavior("onblur")
		{
			@Override
			protected void onUpdate(AjaxRequestTarget target) 
			{
				bean.setJumpPage(txtJumpPage.getModelObject());
				Page initialPage = new Page(bean.getPerPageRecord(),bean.getJumpPage());
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(txtJumpPage);
				
				target.add(ctnList);
				target.add(ctnPageBar);
			}
			
		});
		
		
		txtPerPageRecord.add(new AjaxFormComponentUpdatingBehavior("onblur")
		{
			
			/**	 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				bean.setPerPageRecord(txtPerPageRecord.getModel().getObject());
				Page initialPage = new Page(bean.getPerPageRecord(),bean.getCurrPageNum());
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		});
		
		final AjaxLink<Void> firstPage=new AjaxLink<Void>("firstPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(), 1);
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};	
		
		
		final AjaxLink<Void> upPage=new AjaxLink<Void>("upPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				bean.setCurrPageNum(bean.getCurrPageNum()-1);
				
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum() );
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};	
			 
		final AjaxLink<Void> nextPage=new AjaxLink<Void>("nextPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
					bean.setCurrPageNum(bean.getCurrPageNum()+1);
				
					Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getCurrPageNum());
					SimpleDataView.this.onLoadPage(initialPage);
					refresh(initialPage);
					target.add(ctnList);
					target.add(ctnPageBar);
			}
		};
		
		final AjaxLink<Void> lastPage=new AjaxLink<Void>("lastPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getTotalPage());
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};
		
		final AjaxLink<Void> jumpPage=new AjaxLink<Void>("jump") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getJumpPage());
				SimpleDataView.this.onLoadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};
		
		
		add(ctnList);
		{
			ctnList.add(group);
			group.add(groupSelector);
			{
				group.add(header);
				group.add(bodyer);
			}
		}
		
		add(ctnPageBar);
		{
			ctnPageBar.add(lblTotalRecord);
			ctnPageBar.add(lblTotalPage);
			ctnPageBar.add(txtPerPageRecord);
			ctnPageBar.add(lblCurrPageNum);
			ctnPageBar.add(txtJumpPage);
			ctnPageBar.add(jumpPage);
			ctnPageBar.add(firstPage);
			ctnPageBar.add(upPage);
			ctnPageBar.add(nextPage);
			ctnPageBar.add(lastPage);
		}
		add(modalWindow);
	}
	
	/**
	 * 表格控件必须调用的数据刷新方法,UI才会更新.
	 * @param page 
	 */
	public void refresh(Page page) {
		this.bodyerData.clear();
		if(page.getData()!=null){
			this.bodyerData.addAll(page.getData());
			
		}
		
		
		bean.setTotalRecord(page.getTotalRecord());
		bean.setTotalPage(page.getTotalPage());
		bean.setPerPageRecord(page.getPerPageRecord());
		bean.setCurrPageNum(page.getCurrPageNum());
		bean.setJumpPage(page.getCurrPageNum());
		
	}
	/*
	@Override
	public boolean isVisible()
	{
		 super.isVisible();
		 if(bodyerData.size()==0){
			 return false;
		 }
		 return true;
	}
	*/
	public SimpleDataView addHeadLabel(String [] lable){
		headerData.clear();
		for(String i: lable){
			headerData.add(i);
		}
		return this;
	}
	
	public SimpleDataView addHeadProp(String [] props){
		headerProperty.clear();
		for(String i: props){
			headerProperty.add(i);
		}
		return this;
	}
	 
	
	public ModalWindow getModalWindow(){
		return modalWindow;
	}
	
	public Integer getCurrPage(){
		return bean.getCurrPageNum();
	}
	
	public Integer getPerPageRecord(){
		return bean.getPerPageRecord();
	}
	
	/**
	 * 必须实现的方法.
	 * @param page 不带数据的初使分页对象
	 */
	protected void onLoadPage(Page page) {
		
	}
	
	protected void onClickDelete(AjaxRequestTarget target, IModel<Object> rowModel)
	{

	}

	protected void onClickUpdate(AjaxRequestTarget target, IModel<Object> rowModel)
	{

	}

	protected void onClickCreate(AjaxRequestTarget target, IModel<Object> rowModel)
	{

	}


}