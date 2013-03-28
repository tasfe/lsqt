package org.lsqt.content.web.wicket.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.PropertyModel;
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
import org.lsqt.content.web.wicket.component.form.SimpleForm;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;
import org.lsqt.content.web.wicket.util.RendererUtil;

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
	
	
	final SimpleTree tree=(SimpleTree) new SimpleTree("tree")
	{
		public java.util.List<Node> onLoadTree() 
		{
			return rebuildTreeData();
		};
		
		@Override
		protected void onClickNode(AjaxRequestTarget target, Node node)
		{
			
		}
	}.setOutputMarkupId(true);
	
	Node root;
	private List<Node> rebuildTreeData()
	{
		List<Node> nodes=new ArrayList<Node>();
		
		root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName(ROOT_TEXT);
		root.setType(NODE_TYPE_OTHER);

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName()==null ? "":"[应用]".concat(a.getName()));
			n.setType(NODE_TYPE_APPLICATION);

			List<Category> list = categoryServ.getCategoryByApp(a.getId());
			for (Category c : list)
			{
				c.setName(c.getName());
				nestedCategory(n, c, c.getSubCategories());
			}
		}
		nodes.add(root);
		return nodes;
	}
	
	final ResourceFormBean bean=new ResourceFormBean(); 
	
	public ResourceListPage(){
		SimpleForm form=new SimpleForm("form");
		
		
		final WebMarkupContainer ctnSearch=new WebMarkupContainer("search");
		@SuppressWarnings({"rawtypes", "unchecked"})
		DropDownChoice selResources = new DropDownChoice("selResources",
				new PropertyModel<String>(bean, "selectedItem"),
				Arrays.asList(new String[]{"全局资源", "模板资源"}),
				new IChoiceRenderer<String>()
				{

					@Override
					public Object getDisplayValue(String object)
					{
						return object;
					}

					@Override
					public String getIdValue(String object, int index)
					{
						return object;
					}
				});
		

		tree.expand(root);
	
		add(form);
		{
			form.add(ctnSearch);
			{
				ctnSearch.add(selResources);
			}
			form.add(tree);
			form.add(table);
			
		}
		
		
	}

	class ResourceFormBean implements Serializable
	{
		private String selectedItem;
		private String keyWord;
		private Node clickedNode;
		
		public Node getClickedNode()
		{
			return clickedNode;
		}

		public void setClickedNode(Node clickedNode)
		{
			this.clickedNode = clickedNode;
		}

		public String getKeyWord()
		{
			return keyWord;
		}

		public void setKeyWord(String keyWord)
		{
			this.keyWord = keyWord;
		}

		public String getSelectedItem()
		{
			return selectedItem;
		}

		public void setSelectedItem(String selectedItem)
		{
			this.selectedItem = selectedItem;
		}
	}
}
