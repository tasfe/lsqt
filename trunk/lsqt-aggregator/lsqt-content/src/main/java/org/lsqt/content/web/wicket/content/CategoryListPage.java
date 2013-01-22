package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.PageCreator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.MatchWay;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;
public class CategoryListPage extends ConsoleIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	final SimpleDataView ctnCategoryList=(SimpleDataView) new SimpleDataView("categoryList")
	.addHeadLabel(new String[]{"站点名称","描述","序号","创建时间"})
	.addHeadProp(new String[]{"name","description","orderNum","createTime"})
	.setOutputMarkupPlaceholderTag(true);
	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("ctnSearch").setOutputMarkupId(true);
	
	public CategoryListPage(){
		
		
		
		List<Node> nodes = new ArrayList<Node>();
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName("网站列表");

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
		}
		nodes.add(root);

		 
		
		final SimpleTree tree = (SimpleTree) new SimpleTree("tree", nodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				Page page=new Page(20,1);
				categoryServ.getCategoryByApp(node.getId(), page) ;
				ctnCategoryList.refresh(page);
				
			/*	if(page.getData()==null || page.getData().size()==0){
					ctnCategoryList.setVisible(false);
					ctnCategoryList.getModalWindow().setVisible(false);
				}else{
					ctnCategoryList.setVisible(true);
					ctnCategoryList.getModalWindow().setVisible(true);
				}*/
				target.add(ctnCategoryList);
			}
		}.setOutputMarkupPlaceholderTag(true);
		
		
		
		AutoCompleteTextField<String> key=new AutoCompleteTextField<String>("key",new PropertyModel<String>(this, "keyWord")){
			@Override
			protected Iterator<String> getChoices(String input)
			{
				List<String> list=new ArrayList();
				if(StringUtils.isNotEmpty(input))
				{
					Page page=new Page(20, 1);
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE));
					categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
					list.addAll(page.getData());
				}
				return list.iterator();
			}
		};
		
		AjaxLink<Void> btnSearch=new AjaxLink<Void>("btnSearch")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				Page page=new Page(20, 1);
				page.addConditions(new Condition().like("name", getKeyWord(), MatchWay.ANYWHERE));
				categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
				ctnCategoryList.refresh(page);
				
				target.add(ctnCategoryList);
			}
		};
		 
		final CategoryAddWindowClosedCallback callBack=new CategoryAddWindowClosedCallback(tree);
		AjaxLink<Void> btnAdd = new AjaxLink<Void>("btnAdd")
		{
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null){
					warn("请选择某一个应用");
					return ;
				}
				
				final ModalWindow modal=ctnCategoryList.getModalWindow();
				modal.setTitle("添加栏目");
				modal.setCookieName("categoryAdd");
				modal.setWindowClosedCallback(callBack);
				modal.setPageCreator(new PageCreator()
				{
					@Override
					public org.apache.wicket.Page createPage()
					{
						return new CategoryAddPage(tree.getSelectedNode().getId(),getPageReference(),modal);
					}
				});
				
				modal.show(target);
			};
		};
		
		add(tree);
		add(ctnCategoryList);
		add(ctnSearch);
		{
			ctnSearch.add(key);
			ctnSearch.add(btnSearch);
			ctnSearch.add(btnAdd);
		}
	}
	
	private class CategoryAddWindowClosedCallback implements ModalWindow.WindowClosedCallback{
		private SimpleTree tree;
		public CategoryAddWindowClosedCallback(final SimpleTree tree)
		{
			this.tree = tree;
		}
		
		@Override
		public void onClose(AjaxRequestTarget target)
		{
			Page page=new Page(ctnCategoryList.getPerPageRecord(), ctnCategoryList.getCurrPage());
			page.addConditions(new Condition().like("name", getKeyWord(), MatchWay.ANYWHERE));
			categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
			ctnCategoryList.refresh(page);
			
			target.add(ctnCategoryList);
		}
	}
	
	private String keyWord=StringUtils.EMPTY;

	public String getKeyWord()
	{
		return keyWord;
	}

	public void setKeyWord(String key)
	{
		this.keyWord = key;
	}
}
