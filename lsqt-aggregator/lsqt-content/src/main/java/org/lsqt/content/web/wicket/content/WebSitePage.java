package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.NodeProvider;

public class WebSitePage  extends ConsoleIndex{

	@SpringBean AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WebSitePage(){
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
				Node css=new Node(n,UUID.randomUUID().toString(), "css");
				Node img=new Node(n,UUID.randomUUID().toString(), "img");
				Node js=new Node(n,UUID.randomUUID().toString(), "js");
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
		
		Page<Application> initialPage=new Page<Application>(20,1) ;
		initialPage.addOrderProperties("createTime", true);
		appsService.loadPage(initialPage);
		
		final SimpleDateFormat df=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		final List<Application> data=new ArrayList<Application>(initialPage.getData());
		ListDataProvider<Application> dataProvide=new ListDataProvider<Application>(data);
		
		DataView<Application> dataView=new DataView<Application>("rows",dataProvide) {
		
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(final Item<Application> item) {
				item.setOutputMarkupId(true);
				
				String seq=String.valueOf(item.getIndex()+1);
				Label lblIndex=new Label("seq",seq.length()<2 ? "0"+seq: seq );
				item.add(lblIndex);
				
				
				Label lblAppName=new Label("appName", item.getModelObject().getName());
				item.add(lblAppName);
				
				Date dt=new Date(item.getModelObject().getCreateTime());
				Label lblCreateTime=new Label("createTime",df.format(dt));
				item.add(lblCreateTime);
				
				Label lblDesc=new Label("desc", item.getModelObject().getDescription());
				item.add(lblDesc);
				
				
				AjaxLink<Void> btnDelete=new AjaxLink<Void>("btnDelete") {
					/**  */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						
						String id=item.getModelObject().getId();
						appsService.deleteById(id);
						data.remove(item.getModelObject());
						target.add(ctnAppList);
					}
				};
				item.add(btnDelete);
				
				AjaxLink<Void> btnUpdate=new AjaxLink<Void>("btnUpdate") {
					/**  */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params=new PageParameters();
						params.set("appId", item.getModelObject().getId());
						setResponsePage(AppUpdatePage.class, params);
					}
				};
				item.add(btnUpdate);
				
				
				
				AjaxLink<Void> btnManage=new AjaxLink<Void>("btnManage") {
					/**  */
					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						PageParameters params=new PageParameters();
						params.set("appId", item.getModelObject().getId());
						//setResponsePage(AppUpdatePage.class, params);
					}
				};
				item.add(btnManage);
				
				
			}
			
			
		};
		
		
		
		
		add(ctnAppList);
		add(ctnSearch);
		
		ctnAppList.add(dataView);
	}
}
