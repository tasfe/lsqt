package org.lsqt.content.web.wicket.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.content.bean.Node;
import org.lsqt.content.web.wicket.content.bean.NodeProvider;

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
		
		List<Node> nodes=new ArrayList<Node>();
		Node root = new Node("应用根目录");
		{
			for(Application a: apps){
				Node n=new Node(root, a.getName());
			}
		}
		nodes.add(root);

		
		 NodeProvider nodeProvider=new NodeProvider(nodes);
		 
		add(new DefaultNestedTree<Node>("tree", nodeProvider)) ;
	}
	
	private WebMarkupContainer cntCategoryList;
	private void layout() {
		cntCategoryList=new WebMarkupContainer("categoryList");
		cntCategoryList.setOutputMarkupId(true);
		
		add(cntCategoryList);
	}
}
