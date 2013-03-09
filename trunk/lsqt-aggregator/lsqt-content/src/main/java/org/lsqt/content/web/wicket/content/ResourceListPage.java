package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;

public class ResourceListPage  extends ConsoleIndex{

	@SpringBean(name="newsServiceImpl") NewsService newsServ;
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ROOT_TEXT="网站列表";
	public static final String NODE_TYPE_APPLICATION="_application";
	public static final String NODE_TYPE_CATEGORY="_category";
	public static final String NODE_TYPE_OTHER="_other";

	final List<Node> nodes = new ArrayList<Node>();
	private void freshTree()
	{
		nodes.clear();
		
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName(ROOT_TEXT);
		root.setType(NODE_TYPE_OTHER);

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
			n.setType(NODE_TYPE_APPLICATION);

			List<Category> list = categoryServ.getCategoryByApp(a.getId());
			for (Category c : list)
			{
				nestedCategory(n, c, c.getSubCategories());
			}
		}
		nodes.add(root);
	}
	
	private void nestedCategory(Node n, Category c, Set<Category> subs)
	{
		Node node = new Node(n, c.getId(), c.getName());
		node.setType(NODE_TYPE_CATEGORY);
		for (Category t : subs)
		{
			nestedCategory(node, t, t.getSubCategories());
		}
	}

	final SimpleDataView table=(SimpleDataView) new SimpleDataView("table")
	{
		@Override
		protected void onLoadPage(Page page)
		{
			
		}
		
		protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			
		};
	}
	.addHeadLabel(new String[]{"标题","作者","排序号","创建日期","是否启用","是否已发布"})
	.addHeadProp(new String[]{"title","shortContent","orderNum","createTime","isEnable","isPublished"})
	.setOutputMarkupPlaceholderTag(true);
	
	
	private SimpleTree tree;
	
	
	public ResourceListPage(){
		freshTree();
		
		tree=(SimpleTree) new SimpleTree("tree")
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				
			}
		}.setOutputMarkupId(true);
		
		
		final WebMarkupContainer ctnSearch=new WebMarkupContainer("search");
		
		

		
	
		
		add(ctnSearch);
		add(tree);
		add(table);
		
	}

}
