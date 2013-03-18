package org.lsqt.content.web.wicket.component.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.core.util.resource.PackageResourceStream;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.velocity.markup.html.VelocityPanel;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.content.AppListPage;
import org.lsqt.content.web.wicket.content.CategoryListPage;
import org.lsqt.content.web.wicket.content.NewsListPage;
import org.lsqt.content.web.wicket.content.ResourceListPage;
import org.lsqt.content.web.wicket.content.TemplateListPage;

/**
 * 侧边滑动菜单组件,该菜单只支持两组结构菜单,多级结构请用Tree组件.
 * 利用velocity模板解析了固定格式的HTML.
 * @author 袁明敏
 *
 */
@SuppressWarnings("serial")
public class SimpleSDMenu extends Panel
{
	/**最后点击的结点**/
	private Node clickedNode;
	
	public Node getClickedNode()
	{
		return clickedNode;
	}

	public void setClickedNode(Node clickedNode)
	{
		this.clickedNode = clickedNode;
	}


	/**菜单展现的结点数据**/
	private List<Node>  nodes=new ArrayList<Node>();
	
	/**
	 * <pre>
	 * 构造一级、二级结点关系.
	 * Map<Node:当前结点 ,List<Node>:当前结点的下级结点>
	 * </pre>
	 * @return 返回菜单结点数据
	 */
	private Map<Node,List<Node>> getItemMap()
	{
		Map<Node,List<Node>> map=new LinkedHashMap<Node,List<Node>>();
		for(Node node: nodes)
		{
			Node parent=node.getParent();
			for(Node n: nodes)
			{
				if(node!=n)
				{
					if(!nodes.contains(parent))
					{
						map.put(node, getChilds(node));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 获取参数结点的下级结点.
	 * @param node 当前结点
	 * @return List<Node> 返回当前结点的下级结点
	 */
	private List<Node> getChilds(Node node)
	{
		Set<Node> set=new HashSet<Node>();
		
		for(Node n: node.getSubNodes())
		{
			if(nodes.contains(n)){
				set.add(n);
			}
		}
		return new ArrayList<Node>(set);
	}
	
	private  String getID(){
		return UUID.randomUUID().toString().replace("-", "-");
	}
	/*
	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		UrlNode root=new UrlNode(getID(),"CMS管理");		
		UrlNode n1=new UrlNode(root,getID(),"应用管理",AppListPage.class.getName());
		UrlNode n2=new UrlNode(root,getID(),"类别管理",CategoryListPage.class.getName());
		//System.out.println(Class.forName(AppListPage.class.getName()).newInstance());
		System.out.println(n1);
		System.out.println(n2);
	}
	*/
	public  void initData()
	{
		nodes.clear();
		
		UrlNode root=new UrlNode(getID(),"CMS管理");
		UrlNode n1=new UrlNode(root,getID(),"应用管理",AppListPage.class.getName());
		UrlNode n2=new UrlNode(root,getID(),"类别管理",CategoryListPage.class.getName());
		UrlNode n3=new UrlNode(root,getID(),"内容管理",NewsListPage.class.getName());
		UrlNode n4=new UrlNode(root,getID(),"模板管理",TemplateListPage.class.getName());
		UrlNode n5=new UrlNode(root,getID(),"资源管理",ResourceListPage.class.getName());
		
		UrlNode root2=new UrlNode(getID(),"论坛管理");
		UrlNode n6=new UrlNode(root2,getID(),"板块管理");
		UrlNode n7=new UrlNode(root2,getID(),"贴子管理");
		UrlNode n15=new UrlNode(root2,getID(),"模板管理");
		
		UrlNode root3=new UrlNode(getID(),"组织管理");
		UrlNode n8=new UrlNode(root3,getID(),"用户管理");
		UrlNode n9=new UrlNode(root3,getID(),"角色管理");
		UrlNode n10=new UrlNode(root3,getID(),"权限管理");
	
		
		UrlNode root4=new UrlNode(getID(),"商城管理");
		UrlNode n11=new UrlNode(root4,getID(),"产品管理");
		UrlNode n12=new UrlNode(root4,getID(),"模板管理");
		UrlNode n13=new UrlNode(root4,getID(),"订单管理");
		UrlNode n14=new UrlNode(root4,getID(),"物流管理");
		
		nodes.add(root);
		nodes.add(root2);
		nodes.add(root3);
		nodes.add(root4);
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);
		nodes.add(n5);
		nodes.add(n6);
		nodes.add(n7);
		nodes.add(n8);
		nodes.add(n9);
		nodes.add(n10);
		nodes.add(n11);
		nodes.add(n12);
		nodes.add(n13);
		nodes.add(n14);
		nodes.add(n15);
		Map<Node,List<Node>> map=getItemMap();
		System.out.println(map);
	}
	
	
	public SimpleSDMenu(String id,List<Node> nodes)
	{
		super(id);
		initData();
		
	
		Map<String, Map<Node,List<Node>>> map = new LinkedHashMap<String, Map<Node,List<Node>>>();
		Map<Node,List<Node>> itemsMap=getItemMap();
		map.put("itemsMap", itemsMap);
		
		
		VelocityPanel panel;
		add(panel = new VelocityPanel("templatePanel", new MapModel<String, Map<Node,List<Node>>>(map))
		{
			@Override
			protected IResourceStream getTemplateResource()
			{
				return   new PackageResourceStream(SimpleSDMenu.class,"SimpleSDMenu.vm");
			}

			@Override
			protected boolean parseGeneratedMarkup()
			{
				return true;
			}
		});
		for(final Node n: itemsMap.keySet())
		{
			Label title=new Label(n.getId(), n.getName());
			title.add(new AjaxEventBehavior("onclick")
			{
				@Override
				protected void onEvent(AjaxRequestTarget target)
				{
					SimpleSDMenu. this.clickedNode=n;
					onClickNode(target,n);
				}
			});
			panel.add(title);
			
			for(final Node t:itemsMap.get(n))
			{
				AjaxLink<Void> link=new AjaxLink<Void>(t.getId())
				{
					
					@Override
					public void onClick(AjaxRequestTarget target)
					{
						SimpleSDMenu. this.clickedNode=n;
						onClickNode(target,t);
					}
				};
				panel.add(link);
			}
			
		}
		
	}
	
	
	private static final ResourceReference JAVASCRIPT = new JavaScriptResourceReference(
			SimpleSDMenu.class, "res/sdmenu.js");

		private static final ResourceReference CSS = new CssResourceReference(SimpleSDMenu.class,
			"res/sdmenu.css");
		
		
	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		CoreLibrariesContributor.contributeAjax(getApplication(), response);
		response.render(JavaScriptHeaderItem.forReference(JAVASCRIPT));

		ResourceReference cssResource = newCssResource();
		if (cssResource != null)
		{
			response.render(CssHeaderItem.forReference(cssResource));
		}
	}

	/**
	 * Allows to override CSS contribution. Returning null means the CSS will be contributed via
	 * other sources, e.g. a global CSS resource.
	 * 
	 * @return The CSS resource reference or null if CSS is contributed via other means.
	 * @see #setCssClassName(String)
	 */
	protected ResourceReference newCssResource()
	{
		return CSS;
	}

	/**
	 * 点击菜单结点触发的事件调用.
	 */
	protected void onClickNode(AjaxRequestTarget target,Node node)
	{
		
	}
	
	
}
