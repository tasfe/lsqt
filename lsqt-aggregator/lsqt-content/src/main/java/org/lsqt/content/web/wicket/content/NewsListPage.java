package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.datatable.RichDataTable;
import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;
import org.lsqt.content.web.wicket.component.tree.Content;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeExpansionModel;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;
import org.lsqt.content.web.wicket.component.tree.SelectableFolderContent;


public class NewsListPage  extends ConsoleIndex{

	@SpringBean NewsService newsServ;
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NewsListPage(){
		layout();
		
		
		Link<Void> btnAdd=new Link<Void>("btnAdd") {
			/** 	 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(NewsAddPage.class);
			}
		};
		add(btnAdd);
		
		
		loadTree();
		
		initDataView();
	}
	
	private void loadTree(){
		List<Application> apps = appsService.findAll();
		
		List<Node> nodes=new ArrayList<Node>();
		Node root=new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName("网站列表");
		
		for(Application a: apps){
			Node n=new Node(root,a.getId(), a.getName());
			
			for(Category c:a.getCategories()){
				Node tmp=new Node(n,c.getId(), c.getName());
				tmp.setId(c.getId());
			}
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
		Page<Application> initialPage=new Page<Application>(1, 1);
		//initialPage.addOrderProperties("createTime", true);
		List<Application> apps= new ArrayList<Application>(appsService.loadPage(initialPage).getData());
		
		if(apps.size()>0){
			Application firstApp=apps.get(0);
			List<News> list= newsServ.getNewsByApp(firstApp.getId());
			ListDataProvider<News> listDataProvider=new ListDataProvider<News>(list);
			
			final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		 	final DataView<News> dataView=new DataView<News>("rows",listDataProvider) {
				 /**  */ 
				private static final long serialVersionUID = 1L;
				@Override
				protected void populateItem(Item<News> item) {
					String seq=String.valueOf(item.getIndex()+1);
					Label lblIndex=new Label("seq",seq.length()<2 ? "0".concat(seq): seq );
					item.add(lblIndex);
					
					
					Label lblTitle=new Label("title", item.getModelObject().getTitle());
					item.add(lblTitle);
					
					Category c=item.getModelObject().getCategories().iterator().next();
					Label lblCategoryName=new Label("categoryName",c==null ? StringUtils.EMPTY: c.getName());
					item.add(lblCategoryName);
					
					
					if(item.getModelObject().getPubTime()==null){
						Label lblPubTime=new Label("pubTime",StringUtils.EMPTY);
						item.add(lblPubTime);
					}else{
						Date dt=new Date(item.getModelObject().getPubTime());
						Label lblPubTime=new Label("pubTime",df.format(dt));
						item.add(lblPubTime);
					}
					
					 
					Label lblIsEnable=new Label("isEnable",item.getModelObject().getIsEnable() ? "启用":"禁用");
					item.add(lblIsEnable);
					
					
					Label lblIsStatic=new Label("isStatic", item.getModelObject().getIsStatic() ? "已生成":"未生成");
					item.add(lblIsStatic);
				}
			};
			cntNewsList.add(dataView); 
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
	

	private WebMarkupContainer cntNewsList;
	private void layout() {
		cntNewsList=new WebMarkupContainer("newsList");
		cntNewsList.setOutputMarkupId(true);
		
		add(cntNewsList);
	}
}
