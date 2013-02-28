package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;

public class ResourceListPage  extends ConsoleIndex{

	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceListPage(){
		layout();
		loadTree();
	}
	
	public static void main(String args[]){
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
	}
	
	private void loadTree(){
		
		List<Node> nodes=new ArrayList<Node>();
		
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName("站点根目录");
		{
			for(Application a: appsService.findAll()){
				Node n=new Node(root,a.getId(), a.getName());
				/*Node css=new Node(n,UUID.randomUUID().toString(), "css");
				Node img=new Node(n,UUID.randomUUID().toString(), "img");
				Node js=new Node(n,UUID.randomUUID().toString(), "js");*/
			}
		}
		nodes.add(root);

		
		NodeProvider nodeProvider=new NodeProvider(nodes);
		add(new DefaultNestedTree<Node>("tree", nodeProvider)) ;
	}
	
	
	private void layout(){
		final WebMarkupContainer ctnSearch=new WebMarkupContainer("search");
		
		
		final WebMarkupContainer ctnAppList=new WebMarkupContainer("appList");
		ctnAppList.setOutputMarkupId(true);
	
		
		
		
		add(ctnAppList);
		add(ctnSearch);
		
		
	}
}
