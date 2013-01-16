package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
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
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.content.bean.Node;
import org.lsqt.content.web.wicket.content.bean.NodeExpansion;
import org.lsqt.content.web.wicket.content.bean.NodeExpansionModel;
import org.lsqt.content.web.wicket.content.bean.NodeProvider;
import org.lsqt.content.web.wicket.content.bean.SelectableFolderContent;
import org.lsqt.content.web.wicket.content.bean.Content;
public class CategoryListPage extends ConsoleIndex {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	public CategoryListPage(){
		
		layout();
		
		loadTree();
	}

	private void loadTree(){
		List<Application> apps = initDataView();
		
		List<Node> nodes=new ArrayList<Node>();
		Node root = new Node("网站列表");
		
		for(Application a: apps){
			Node n=new Node(root, a.getName());
		}
		
		nodes.add(root);

		
		
		
		NodeProvider nodeProvider = new NodeProvider(nodes);
		
		content = new SelectableFolderContent(nodeProvider);
		
		tree = (NestedTree<Node>) createTree(nodeProvider, new NodeExpansionModel());
		
		theme=new org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme();
		
		tree.add(new Behavior()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void onComponentTag(Component component, ComponentTag tag)
			{
				theme.onComponentTag(component, tag);
			}

			@Override
			public void renderHead(Component component, IHeaderResponse response)
			{
				theme.renderHead(component, response);
			}
		});
		
		add(tree);
		
	}

	private List<Application> initDataView() {
		List<Application> apps= appsService.findAll();
		
		if(apps.size()>0){
			Application firstApp=apps.get(0);
			List<Category> list= categoryServ.getCategoryByApp(firstApp.getId());
			ListDataProvider<Category> listDataProvider=new ListDataProvider<Category>(list);
			
			final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		 	final DataView<Category> dataView=new DataView<Category>("rows",listDataProvider) {
				 /**  */ 
				private static final long serialVersionUID = 1L;
				@Override
				protected void populateItem(Item<Category> item) {
					String seq=String.valueOf(item.getIndex()+1);
					Label lblIndex=new Label("seq",seq.length()<2 ? "0".concat(seq): seq );
					item.add(lblIndex);
					
					
					Label lblName=new Label("name", item.getModelObject().getName());
					item.add(lblName);
					
					Date dt=new Date(item.getModelObject().getCreateTime());
					Label lblCreateTime=new Label("createTime",df.format(dt));
					item.add(lblCreateTime); 
					
					Label lblDesc=new Label("desc", item.getModelObject().getDescription());
					item.add(lblDesc);
				}
			};
			cntCategoryList.add(dataView); 
		}
		return apps;
	}
	
	private Behavior theme;
	

	private NestedTree<Node> tree;
	private Content content;
	protected AbstractTree<Node> createTree(NodeProvider provider, IModel<Set<Node>> state)
	{
		tree = new NestedTree<Node>("tree", provider, state)
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id, IModel<Node> model)
			{
				return content.newContentComponent(id, tree, model);
			}
		};
		return tree;
	}
	
	
	
	
	
	private WebMarkupContainer cntCategoryList;
	private void layout() {
		cntCategoryList=new WebMarkupContainer("categoryList");
		cntCategoryList.setOutputMarkupId(true);
		
		add(cntCategoryList);
	}
}
