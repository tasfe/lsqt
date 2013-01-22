package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
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
	
	public NewsListPage(){
		List<Application> list=appsService.findAll();
		Node root=new Node();
		root.setName("应用");
		root.setId(UUID.randomUUID().toString());
		
		List<Node> nodes=new ArrayList<Node>();
		nodes.add(root);
		for(Application a: list)
		{
			Node n=new Node(root,a.getId(),a.getName());
			
		}
		SimpleTree tree=(SimpleTree) new SimpleTree("tree", nodes).setOutputMarkupId(true);
		
		SimpleDataView dataView=(SimpleDataView) new SimpleDataView("newsList")
		{
			@Override
			protected void onLoadPage(Page page)
			{
				
			}
		}.setOutputMarkupId(true);
		
		
		Link<Void> btnAdd=new Link<Void>("btnAdd") {
			/** 	 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(NewsAddPage.class);
			}
		};
		add(btnAdd);
		
		
		add(tree);
		add(dataView);
	}
	
}
