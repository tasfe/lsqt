package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
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
import org.lsqt.content.web.wicket.component.tree.SimpleTree;


public class NewsListPage  extends ConsoleIndex{
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
	
	
	final SimpleDataView dataView=(SimpleDataView) new SimpleDataView("newsList")
	{
		@Override
		protected void onLoadPage(Page page)
		{
			
		}
		
		protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			News tNews=(News)rowModel.getObject();
			newsServ.deleteById(tNews.getId());
			loadPage(target, tree.getSelectedNode());
		};
	}
	.addHeadLabel(new String[]{"标题","作者","排序号","创建日期","是否启用","是否已发布"})
	.addHeadProp(new String[]{"title","shortContent","orderNum","createTime","isEnable","isPublished"})
	.setOutputMarkupPlaceholderTag(true);
	
	/**
	 * 页面总数据刷新、加载的方法.
	 * @param target -
	 * @param node 用户选择的树结点
	 */
	private void loadPage(AjaxRequestTarget target, Node node)
	{
		Page page=new Page(dataView.getPerPageRecord(),dataView.getCurrPage());
		
		if(NODE_TYPE_APPLICATION.equals(node.getType())){
			newsService.getPageByAppID(node.getId(), page);
		}else if(NODE_TYPE_CATEGORY.equals(node.getType())){
			newsService.getPageByCategoryID(node.getId(), page);
		}else {
			return ;
		}
		
		dataView.refresh(page);
		target.add(dataView);
	}
	
	private SimpleTree tree;
	@SuppressWarnings("serial")
	public NewsListPage(){
		freshTree();
		
		Form form=new Form("form");
		
		tree=(SimpleTree) new SimpleTree("tree")
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				loadPage(target, node);
			}
		}.setOutputMarkupId(true);
		

		
		

		
		AjaxLink<Void> btnAdd = new AjaxLink<Void>("btnAdd")
		{

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null)
				{
					target.appendJavaScript("alert('请选择一个栏目再进行添加内容!')");
					return ;
				}
				if(! NODE_TYPE_CATEGORY.equals(tree.getSelectedNode().getType()))
				{
					target.appendJavaScript("alert('请选择一个栏目再进行添加内容!')");
					return ;
				}
				final ModalWindow modal=dataView.getModalWindow();
				modal.setPageCreator(new ModalWindow.PageCreator()
				{
					
					@Override
					public org.apache.wicket.Page createPage()
					{
						return new NewsAddPage(tree.getSelectedNode())
						{
							@Override
							protected void onSaveAfter(AjaxRequestTarget target)
							{
								modal.close(target);
							}
						};
					}
				});
				
				
				modal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
				{
					@Override
					public void onClose(AjaxRequestTarget target)
					{
						loadPage(target, tree.getSelectedNode());
					}
				});
				modal.setInitialWidth(800);
				modal.setInitialHeight(600);
				modal.show(target);
			}
		};
		

		
		add(form);
		{
		form.add(btnAdd);
		form.add(tree);
		form.add(dataView);
		}
	}
	
}
