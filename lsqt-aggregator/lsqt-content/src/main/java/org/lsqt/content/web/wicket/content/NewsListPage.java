package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;


public class NewsListPage  extends ConsoleIndex{

	@SpringBean NewsService newsServ;
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ROOT_TEXT="网站列表";
	private static final String NODE_TYPE_APPLICATION="_application";
	private static final String NODE_TYPE_CATEGORY="_category";
	private static final String NODE_TYPE_OTHER="_other";

	
	final List<Node> nodes = new ArrayList<Node>();
	private void freshTree()
	{
		nodes.clear();
		
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName(ROOT_TEXT);
		root.setTag(NODE_TYPE_OTHER);

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
			n.setTag(NODE_TYPE_APPLICATION);

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
		node.setTag(NODE_TYPE_CATEGORY);
		for (Category t : subs)
		{
			nestedCategory(node, t, t.getSubCategories());
		}
	}
	
	
	final SimpleDataView dataView=(SimpleDataView) new SimpleDataView("newsList")
	{
		@Override
		protected void onLoadPage(Page page)
		{
			
		}
	}
	.addHeadLabel(new String[]{"标题","作者","排序号","创建日期","是否启用","是否已发布"})
	.addHeadProp(new String[]{"title","shortContent","orderNum","createTime","isEnable","isPublished"})
	.setOutputMarkupId(true);
	
	
	SimpleTree tree;
	@SuppressWarnings("serial")
	public NewsListPage(){
		freshTree();
		
		tree=(SimpleTree) new SimpleTree("tree", nodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				
				
			}
		}.setOutputMarkupId(true);
		
		final NewsAddPanel  newsAdd=(NewsAddPanel) new NewsAddPanel("newsAdd")
		{
			@Override
			protected void onSaveAfter(AjaxRequestTarget target)
			{
				super.onSaveAfter(target);
				
			}
		}.setOutputMarkupPlaceholderTag(true);
		
		
		newsAdd.setVisible(false);
		
		AjaxLink<Void> btnAdd = new AjaxLink<Void>("btnAdd")
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null)
				{
					target.appendJavaScript("alert('请选择一个栏目再进行添加新闻!')");
					return ;
				}
				
				newsAdd.setParentCategoryID(tree.getSelectedNode().getId());
				
				if (!newsAdd.isVisible())
				{
					newsAdd.setVisible(true);
					target.add(newsAdd);
				}

				if (dataView.isVisible())
				{
					dataView.setVisible(false);
					target.add(dataView);
				}
			}
		};
		

		
		
		add(btnAdd);
		add(tree);
		add(dataView);
		add(newsAdd);
		
	}
	
}
