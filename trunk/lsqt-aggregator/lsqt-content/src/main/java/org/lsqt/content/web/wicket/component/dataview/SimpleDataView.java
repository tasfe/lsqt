package org.lsqt.content.web.wicket.component.dataview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.lsqt.components.dao.suport.BeanHelper;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;

public class SimpleDataView extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private PagenationBean bean=new PagenationBean();
	
	
	private List<String> headerData=new ArrayList<String>();
	private List<String> headerProperty=new ArrayList<String>();
	
	private List<Object> bodyerData=new ArrayList<Object>();
	public SimpleDataView(String id){
		super(id);
		
		Page temp=new Page(20,1);
		loadPage(temp);
		refresh(temp);
		
		final WebMarkupContainer ctnList=(WebMarkupContainer) new WebMarkupContainer("ctnList").setOutputMarkupId(true);
		
		
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
		
		
		
		ListView<Object> bodyer = new ListView<Object>("bodyer", bodyerData)
		{
			@Override
			protected void populateItem(ListItem<Object> item)
			{
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

				final WebMarkupContainer operats=(WebMarkupContainer) new WebMarkupContainer("operats").setOutputMarkupId(true);
				item.add(operats);
				item.add(row);
			}
		};

		final WebMarkupContainer ctnPageBar=(WebMarkupContainer)new WebMarkupContainer("pageBar").setOutputMarkupId(true);
		
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
				SimpleDataView.this.loadPage(initialPage);
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
				SimpleDataView.this.loadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		});
		
		final AjaxLink<Void> firstPage=new AjaxLink<Void>("firstPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(), 1);
				SimpleDataView.this.loadPage(initialPage);
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
				SimpleDataView.this.loadPage(initialPage);
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
					SimpleDataView.this.loadPage(initialPage);
					refresh(initialPage);
					target.add(ctnList);
					target.add(ctnPageBar);
			}
		};
		
		final AjaxLink<Void> lastPage=new AjaxLink<Void>("lastPage") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getTotalPage());
				SimpleDataView.this.loadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};
		
		final AjaxLink<Void> jumpPage=new AjaxLink<Void>("jump") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				
				Page<Application> initialPage = new Page<Application>(bean.getPerPageRecord(),bean.getJumpPage());
				SimpleDataView.this.loadPage(initialPage);
				refresh(initialPage);
				target.add(ctnList);
				target.add(ctnPageBar);
			}
		};
		
		add(ctnList);
		{
			ctnList.add(header);
			ctnList.add(bodyer);
		}
		
		add(ctnPageBar);
		{
			ctnPageBar.add(lblTotalRecord.setOutputMarkupId(true));
			ctnPageBar.add(lblTotalPage.setOutputMarkupId(true));
			ctnPageBar.add(txtPerPageRecord.setOutputMarkupId(true));
			ctnPageBar.add(lblCurrPageNum.setOutputMarkupId(true));
			ctnPageBar.add(txtJumpPage.setOutputMarkupId(true));
			ctnPageBar.add(jumpPage.setOutputMarkupId(true));
			ctnPageBar.add(firstPage);
			ctnPageBar.add(upPage);
			ctnPageBar.add(nextPage);
			ctnPageBar.add(lastPage);
		}
	}
	
	public void refresh(Page page) {
		this.bodyerData.clear();
		this.bodyerData.addAll(page.getData());
		
		
		bean.setTotalRecord(page.getTotalRecord());
		bean.setTotalPage(page.getTotalPage());
		bean.setPerPageRecord(page.getPerPageRecord());
		bean.setCurrPageNum(page.getCurrPageNum());
		bean.setJumpPage(page.getCurrPageNum());
	}
	
	public void addHeadLabel(String [] lable){
		headerData.clear();
		for(String i: lable){
			headerData.add(i);
		}
	}
	
	public void addHeadProp(String [] props){
		headerProperty.clear();
		for(String i: props){
			headerProperty.add(i);
		}
	}
	 
	protected void loadPage(Page page) {
		
	}
}
