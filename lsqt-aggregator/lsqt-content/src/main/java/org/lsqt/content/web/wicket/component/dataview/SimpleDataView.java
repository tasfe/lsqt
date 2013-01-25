package org.lsqt.content.web.wicket.component.dataview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
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
	
	// because the modal open to others,so ModalWindow#setOutputMarkupPlaceholderTag(true). 
	final ModalWindow modalWindow=(ModalWindow) new ModalWindow("modalWin"); 
	final WebMarkupContainer ctnList=(WebMarkupContainer) new WebMarkupContainer("ctnList").setOutputMarkupPlaceholderTag(true);
	final WebMarkupContainer ctnPageBar=(WebMarkupContainer)new WebMarkupContainer("pageBar").setOutputMarkupPlaceholderTag(true);
	
	public SimpleDataView(String id){
		super(id);
		
		Page temp=new Page(20,1);
		onLoadPage(temp);
		refresh(temp);
		
		
		
		
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
			protected void populateItem(final ListItem<Object> item)
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
				
				item.add(row);
				item.add(operats);
				{
					operats.add(btnDelete.setOutputMarkupId(true));
					operats.add(btnCreate.setOutputMarkupId(true));
					operats.add(btnUpdate.setOutputMarkupId(true));
				}
			}
		};

		
		
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
		add(modalWindow);
	}
	
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
/***
[WICKET-4976](https://issues.apache.org/jira/browse/WICKET-4976) - WicketTester#startComponent(Component) doesn't detach the component and request cycle
Bug

* [WICKET-4906](https://issues.apache.org/jira/browse/WICKET-4906) - Form#visitFormComponents can cause ClassCastException
* [WICKET-4925](https://issues.apache.org/jira/browse/WICKET-4925) - AbstractAjaxBehavior should clean stored reference to a component on unbind
* [WICKET-4927](https://issues.apache.org/jira/browse/WICKET-4927) - Header can not be set from IRequestCycleListener#onEndRequest()
* [WICKET-4928](https://issues.apache.org/jira/browse/WICKET-4928) - Error adding links to WebSocketRequestHandler
* [WICKET-4935](https://issues.apache.org/jira/browse/WICKET-4935) - Rendered URL is resulting with double slash when using AuthenticatedWebApplication
* [WICKET-4939](https://issues.apache.org/jira/browse/WICKET-4939) - AbstractAjaxTimerBehavior never triggers if attached to WebPage
* [WICKET-4948](https://issues.apache.org/jira/browse/WICKET-4948) - Modal window does not center correctly when window is scrolled in safari
* [WICKET-4950](https://issues.apache.org/jira/browse/WICKET-4950) - ResourceStreamLocator#newResourceNameIterator isn't a factory method anymore
* [WICKET-4953](https://issues.apache.org/jira/browse/WICKET-4953) - RangeValidator#decorate mixes error keys
* [WICKET-4954](https://issues.apache.org/jira/browse/WICKET-4954) - Issue with file upload with progress bar via AJAX and Firefox
* [WICKET-4955](https://issues.apache.org/jira/browse/WICKET-4955) - SessionData violates comparison contract
* [WICKET-4956](https://issues.apache.org/jira/browse/WICKET-4956) - compareTo methods of Actions in BufferedWebResponse violate Comparable contract
* [WICKET-4959](https://issues.apache.org/jira/browse/WICKET-4959) - Notify behaviors when a component is removed from the tree
* [WICKET-4961](https://issues.apache.org/jira/browse/WICKET-4961) - wicket ajax submit does not serialize elements of parental forms
* [WICKET-4962](https://issues.apache.org/jira/browse/WICKET-4962) - AjaxFormChoiceComponentUpdatingBehavior cannot be triggered with BaseWicketTester#executeAjaxEvent()
* [WICKET-4965](https://issues.apache.org/jira/browse/WICKET-4965) - NPE when stopping Tomcat
* [WICKET-4968](https://issues.apache.org/jira/browse/WICKET-4968) - NPE in FencedFeedbackPanel#onRemove
* [WICKET-4971](https://issues.apache.org/jira/browse/WICKET-4971) - AtmosphereEventSubscriptionCollector is slow
* [WICKET-4973](https://issues.apache.org/jira/browse/WICKET-4973) - AbstractRequestLogger - infinite ArrayIndexOutOfBoundsException when requestWindow size is 0
* [WICKET-4975](https://issues.apache.org/jira/browse/WICKET-4975) - client side memory leak on  date picker
* [WICKET-4986](https://issues.apache.org/jira/browse/WICKET-4986) - wicket-ajax-jquery.js fails with 'member not found' on IE for delayed ajax requests
Improvement

* [WICKET-4919](https://issues.apache.org/jira/browse/WICKET-4919) - AjaxLazyLoadPanel needs a method to add components to the AjaxRequestTarget when the component is rendered
* [WICKET-4933](https://issues.apache.org/jira/browse/WICKET-4933) - Palette does not handle disabled choices correctly
* [WICKET-4937](https://issues.apache.org/jira/browse/WICKET-4937) - Add IResponseFilter that can filter out invalid XML characters
* [WICKET-4940](https://issues.apache.org/jira/browse/WICKET-4940) - Make MountedMapper#getMatchedSegmentSizes(url) protected
* [WICKET-4957](https://issues.apache.org/jira/browse/WICKET-4957) - Listener needed for registration and removal of pages
* [WICKET-4958](https://issues.apache.org/jira/browse/WICKET-4958) - It should be possible to manipulate AjaxRequestAttributes globally
* [WICKET-4963](https://issues.apache.org/jira/browse/WICKET-4963) - ComponentModel "setObject" methods should take generic "T" type instead of "Object"
* [WICKET-4970](https://issues.apache.org/jira/browse/WICKET-4970) - Move the logic for creating the proper PackageResource from PackageResourceReference to ResourceReferenceRegistry
* [WICKET-4982](https://issues.apache.org/jira/browse/WICKET-4982) - StatelessChecker: add helpful information to find stateful behavior (patch included)
* [WICKET-4983](https://issues.apache.org/jira/browse/WICKET-4983) - extra recursion on Wicket.DOM.get
 * */
 