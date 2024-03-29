package org.lsqt.content.web.wicket.component.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree.State;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class SimpleTree extends Panel
{

	
	/** */
	private static final long serialVersionUID = 1L;

	
	private NestedTree<Node> tree;
	public void expand(Node node)
	{
		if(tree!=null)
		{
			if(tree.getState(node)==State.COLLAPSED)
			{
				tree.expand(node);
			}
		}
	}
	public void collapse(Node node)
	{
		if(tree!=null)
		{
			tree.collapse(node);
		}
	}
	

	protected void onClickNode(AjaxRequestTarget target,Node node){
		
	}
	
	private Node selectedNode;
	
	public Node getSelectedNode(){
		return this.selectedNode;
	}
	
	
	
	final List<Node> datas=new ArrayList<Node>();
	final NodeProvider nodeProvider = new NodeProvider(datas);
	
	/**
	 * 刷新UI所承载的数据.
	 * @param nodes
	 */
	public void refresh(List<Node> nodes)
	{
		datas.clear();
		datas.addAll(nodes);
	}
	
	/**
	 * 初使化时,加载UI所承载的数据.
	 * @return
	 */
	public List<Node> onLoadTree()
	{
		return new ArrayList<Node>(0);
	}
	
	/**
	 * 构造函数.
	 * @param id WebMarkup ID
	 * @param rootList 多个根结点list
	 */
	public SimpleTree(String id)
	{
		super(id);
		
		datas.clear();
		datas.addAll(onLoadTree());
	
		
		
		final Content content = new SelectableFolderContent(nodeProvider)
		{
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onClick(AjaxRequestTarget target)
			{
				
				Node node=getSeleted().getObject();
				
				SimpleTree.this.selectedNode=node;
				
				
				
				onClickNode(target,node);
				
			}
		};
		
		
		tree = new NestedTree<Node>("tree", nodeProvider, new NodeExpansionModel())
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected Component newContentComponent(String id, IModel<Node> model)
			{
				return content.newContentComponent(id, this, model);
			}
		};
		/*if(nodeProvider.getRoots().hasNext())
		{
			tree.expand(nodeProvider.getRoots().next());
		}*/
		 
		
		final Behavior theme=new org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme();
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

}
