package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Content;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeExpansion;
import org.lsqt.content.web.wicket.component.tree.NodeExpansionModel;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;
import org.lsqt.content.web.wicket.component.tree.SelectableFolderContent;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;
public class CategoryListPage extends ConsoleIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	final SimpleDataView ctnCategoryList=(SimpleDataView) new SimpleDataView("categoryList").setOutputMarkupId(true);
	
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

		 
		
		SimpleTree tree = (SimpleTree) new SimpleTree("tree", nodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				Page page=new Page(20,1);
				categoryServ.getCategoryByApp(node.getId(), page) ;
				ctnCategoryList.refresh(page);
				target.add(ctnCategoryList);
			}
		}.setOutputMarkupId(true);
		
		
		
		add(tree);
		add(ctnCategoryList);
	}
}
