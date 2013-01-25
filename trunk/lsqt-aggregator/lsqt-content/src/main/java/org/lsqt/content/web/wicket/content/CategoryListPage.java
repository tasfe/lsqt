package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.MatchWay;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
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
	.addHeadLabel(new String[]{"栏目名称","访问路径","序号","创建时间"})
	.addHeadProp(new String[]{"name","description","orderNum","createTime"})
	.setOutputMarkupPlaceholderTag(true);
	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("ctnSearch").setOutputMarkupId(true);
	
	final CategoryAddPanel categoryAddPanel=(CategoryAddPanel) new CategoryAddPanel("categoryAddPanel")
	{
		protected void onSaveAfter(AjaxRequestTarget target)
		{
			if(selectedNode!=null && selectedNode.getId()!=null){
				Page page=new Page(20,1);
				categoryServ.getCategoryByApp(selectedNode.getId(), page) ;
				ctnCategoryList.refresh(page);
				ctnCategoryList.setVisible(true);
				categoryAddPanel.setVisible(false);
				target.add(ctnCategoryList);
				target.add(categoryAddPanel);
			}
		};
		
		protected void onCancelAfter(AjaxRequestTarget target)
		{
			ctnCategoryList.setVisible(true);
			categoryAddPanel.setVisible(false);
			target.add(ctnCategoryList);
			target.add(categoryAddPanel);
		};
	}.setOutputMarkupPlaceholderTag(true).setVisible(false);
	
	
	private Node selectedNode;
	
	
	private void nestedCategory(Node n,Category c, Set<Category> subs){
		Node node=new Node(n,c.getId(),c.getName());
		for(Category t:subs)
		{
			nestedCategory(node,t,t.getSubCategories());
		}
	}
	public CategoryListPage(){
		
		List<Node> nodes = new ArrayList<Node>();
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName("网站列表");

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
			List<Category>list= categoryServ.getCategoryByApp(a.getId());
			for(Category c: list)
			{
				nestedCategory(n,c,c.getSubCategories());
			}
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
				selectedNode=node;
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
		 

		AjaxLink<Void> btnAdd = new AjaxLink<Void>("btnAdd")
		{
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null){
					warn("请选择某一个应用");
					return ;
				}
				ctnCategoryList.setVisible(false);
				categoryAddPanel.setVisible(true);
				target.add(ctnCategoryList);
				target.add(categoryAddPanel);
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
		add(categoryAddPanel);
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
