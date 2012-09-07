package org.lsqt.content.web.wicket.component.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.markup.html.tree.LabelTree;
import org.apache.wicket.model.IModel;

import org.lsqt.components.dao.suport.BeanHelper;
import org.lsqt.content.web.console.demo.MyDemoEmployee;
import org.lsqt.content.web.console.demo.MyDemoPage;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;
import org.lsqt.content.web.wicket.content.NewsContentPage;

public class SimpleTree extends Panel {
	private Integer  width;
	private Integer height;
	
	/**
	 * 绑定实体对象到树，指定树控件要显示的Bean父子级属性和Lable属性
	 * @param beans
	 * @param idProperty
	 * @param pidProperty
	 * @param displayProperty
	 */
	public void bindData(List<Object> beans,String idProperty, String pidProperty,String displayProperty){
		
		List<String[]> list=new ArrayList<String[]>();
		try{
			for(Object i: beans){
				Object idPropertyValue=getBeanPropertyValue(i, idProperty);  //parent
				Object pidPropertyValue=getBeanPropertyValue(i, pidProperty); //child
				Object displayPropertyValue=getBeanPropertyValue(i, displayProperty); //lable
				
				if(null != idPropertyValue && null!=pidPropertyValue){
					
					String[] row = new String[] { String.valueOf(idPropertyValue),
							String.valueOf(pidPropertyValue),
							null == displayPropertyValue ? "":String.valueOf(displayPropertyValue) };
					list.add(row);
				}else {
					throw new NullPointerException("SimpleTree#bindData() error, parentId and childId must be not null,the root item must be pid=id !");
				}
			}
			
			
			final DefaultMutableTreeNode uiRootNode = new DefaultMutableTreeNode();  //ui root node
			uiRootNode.setUserObject(SimpleTree.this); 
			 
			String [] root=null;
			for(String [] currItem: list){
				if(currItem[0]==currItem[1]){ //root item condition
					root=currItem;
					break;
				}
				if("".equals(currItem[1])){ //or root item condition
					root=currItem;
					break;
				}
			}
			DefaultMutableTreeNode dataRootNode=getRootNode(list);  //data root node
			buidTreeNodes(root,list);
			uiRootNode.add(dataRootNode );
			
			DefaultTreeModel treeModel = new DefaultTreeModel( uiRootNode);
			final Tree tree = new Tree( "tree", treeModel) ;
			add(tree);
		}catch(Exception ex){
			throw new RuntimeException("Binding data failed, resulting in reflect get Bean's value ! ==>"+ex.getMessage());
		}
	}
	
	/**
	 * 获取符合 id＝pid 的，或者符合pid为String.empt的条件的结点为根结点
	 * @param items
	 * @return
	 */
	private DefaultMutableTreeNode getRootNode(List<String []> items){
		for(String [] currItem: items){
			if(currItem[0]==currItem[1]){ //root item condition
				return  new DefaultMutableTreeNode(currItem[2]);
			}
			if("".equals(currItem[1])){ //or root item condition
				return  new DefaultMutableTreeNode(currItem[2]);
			}
		}
		return null;
	}
	
	private  DefaultMutableTreeNode  buidTreeNodes(String [] currItem, List<String []> items){
		
		return getRootNode(items);
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * @param property
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static Object getBeanPropertyValue(Object obj,String property) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		Class clazz=obj.getClass();
		Field file=clazz.getField(property);
		return file.get(obj);
	}
	
	/**
	 * 
	 * @param dataTable
	 * @param idIndex
	 * @param pidIndex
	 * @param displayIndex
	 */
	public void bindData(Object [][] dataTable,int idIndex, int pidIndex, String displayIndex ){
		
	}
	
	public SimpleTree(String id){
		super(id);
		final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		rootNode.setUserObject("系统");
		
		
		for (int i = 0; i < 5; i++) {
			DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("parent-" + i);
			parentNode.setUserObject(i);
			
			for (int j = 0; j < 5; j++) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("child-" + j);
				parentNode.add(childNode);
			}
			rootNode.add(parentNode);
		}
			
		// 构建一个Model
		DefaultTreeModel treeModel = new DefaultTreeModel( rootNode);
		final LinkTree tree = new LinkTree( "tree", treeModel) {
			
			//newContentComponent
			@Override
			protected Component newNodeComponent(String id, IModel<Object> model) {
				return super.newNodeComponent(id, model);
				
			}
			
			/*@Override
			protected void onNodeLinkClicked(AjaxRequestTarget target,
					TreeNode node) {
				super.onNodeLinkClicked(target, node);
				System.out.println(node.toString());
				setResponsePage(org.lsqt.content.web.wicket.content.NewsContentPage.class);
			}
			*/
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node) {
				System.out.println( node.getClass()+"+++++++>");
			};
			
			@Override
			protected void onNodeLinkClicked(Object node, BaseTree tree,
					AjaxRequestTarget target) {
				super.onNodeLinkClicked(node, tree, target);
				DefaultMutableTreeNode d=(DefaultMutableTreeNode)node;
				
				System.out.println(node.getClass()+"======>"+d.getUserObject()+d.getDepth()+"  "+d.getUserObjectPath() );
				for(Object i: d.getUserObjectPath() ){
					System.out.println(i);
				}
				setResponsePage(NewsContentPage.class);
			//	target.add(tree);
			}/**/
			
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				// tag.put("style", "white-space: nowrap;width: 60em;overflow: auto;margin: 0px;line-height: 1.5em; "); 
			}
			
			protected String getNodeLabel(DefaultMutableTreeNode node) {
				if(node.isRoot()) {
					return "root";
				}else{
					return node.toString();
				}
				// 重载getNodeLabel方法以显示相应的字符串
			}

			@Override
			protected void populateTreeItem(WebMarkupContainer item, int level) {
				// TODO Auto-generated method stub
				
				super.populateTreeItem(item, level);
				
			}
				
			
		};
		
		add(tree);
		add(new AjaxLink("expandAll") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				tree.getTreeState().expandAll();
				target.add(tree);
			}
		});
		
		add(new AjaxLink("collapseAll") {
			public void onClick(AjaxRequestTarget target) {
				tree.getTreeState().collapseAll();
				target.add(tree);
				// 收缩根结点
			}
		});
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
}
