package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;

/**
 * 站点内容的模板管理,模版主要是Velocity文件.
 * @author 袁明敏
 *
 */
@SuppressWarnings("serial")
public class TemplateListPage extends ConsoleIndex
{
	@SpringBean(name="newsServiceImpl") NewsService newsServ;
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	private static final String ROOT_TEXT="网站列表";
	public static final String NODE_TYPE_APPLICATION="_application";
	public static final String NODE_TYPE_CATEGORY="_category";
	public static final String NODE_TYPE_OTHER="_other";
	
	SimpleTree tree;
	SimpleDataView table;
	public TemplateListPage()
	{
		freshTree();
		tree=new SimpleTree("tree", treeNodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				
			}
		};
		
		table =(SimpleDataView)new SimpleDataView("table")
		{
			
		}.addHeadLabel(new String[]{"文件名","文件别名","描述","大小"})
		
		.setOutputMarkupId(true);
		
		add(tree);
		add(table);
	}
	
	
	
	final List<Node> treeNodes = new ArrayList<Node>();
	/**
	 * 刷新树结构,保持数据与数据库同步.
	 */
	private void freshTree()
	{
		treeNodes.clear();
		
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
		treeNodes.add(root);
	}
	
	/**
	 * freshTee()的辅助方法,递归设置结点上下级结构关系.
	 * @param n 当前结点,每一个category别作为一个node放置
	 * @param c 类别 当前结点承载的类别
	 * @param subs 当前结点的子类别
	 */
	private void nestedCategory(Node n, Category c, Set<Category> subs)
	{
		Node node = new Node(n, c.getId(), c.getName());
		node.setType(NODE_TYPE_CATEGORY);
		for (Category t : subs)
		{
			nestedCategory(node, t, t.getSubCategories());
		}
	}
	
	
}
