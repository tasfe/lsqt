package org.lsqt.content.web.wicket.component.tree;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tree.DefaultTreeState;
import org.apache.wicket.extensions.markup.html.tree.ITreeState;
import org.apache.wicket.extensions.markup.html.tree.Tree;
/*import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.DefaultTreeState;
import org.apache.wicket.markup.html.tree.ITreeState;
import org.apache.wicket.markup.html.tree.LinkTree;*/
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.content.NewsAddPage;

public class MyTree extends Tree {
	private static final String TREE_STATE_SESSION_KEY="_tree_state_session_key";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyTree(String id) {
		super(id);
	}

	private List<?> beans;
	private String idProperty;
	private String pidProperty;
	private String displayProperty;
	
	@Override
	protected ITreeState newTreeState() {
		Session session = this.getSession();
		System.out.println(this.getSession().getId());
		
		System.out.println("session.size:" + session.getSizeInBytes());
		ITreeState treeState = (ITreeState) session.getAttribute(TREE_STATE_SESSION_KEY);
		if (treeState == null) {
			treeState = new DefaultTreeState();
			session.setAttribute(TREE_STATE_SESSION_KEY, treeState);
		}
		return treeState;
	}
	
	
	@Override
	protected void onJunctionLinkClicked(AjaxRequestTarget target, TreeNode node) {
		super.onJunctionLinkClicked(target, node);
		if (getTreeState().isNodeExpanded(node)) {
			getTreeState().collapseNode(node);
		}else{
			getTreeState().expandNode(node);
		}
		System.out.println(this.getSession().getId()+"  ===>"+getTreeState());
		this.getSession().setAttribute(TREE_STATE_SESSION_KEY, getTreeState());
	}
	
	@Override
	protected void onNodeLinkClicked(AjaxRequestTarget target, TreeNode node) {
		super.onNodeLinkClicked(target, node);
		if (getTreeState().isNodeExpanded(node)) {
			getTreeState().collapseNode(node);
		}else{
			getTreeState().expandNode(node);
		}
		System.out.println(this.getSession().getId());
		System.out.println(this.getSession().getId()+"  ===>"+getTreeState());
		this.getSession().setAttribute(TREE_STATE_SESSION_KEY, getTreeState());
		setResponsePage(NewsAddPage.class);
	}
	
/*	@Override
	protected void onNodeLinkClicked(Object node, BaseTree tree,
			AjaxRequestTarget target) {
		super.onNodeLinkClicked(node, tree, target);
		if (getTreeState().isNodeExpanded(node)) {
			getTreeState().collapseNode(node);
		}else{
			getTreeState().expandNode(node);
		}
		setResponsePage(NewsContentPage.class);
	}*/
	
	/**
	 * 绑定实体对象到树，指定树控件要显示的Bean父子级属性和Lable属性.
	 * @param beans
	 * @param idProperty
	 * @param pidProperty
	 * @param displayProperty
	 */
	public void bindData(List<?> beans,String idProperty, String pidProperty,String displayProperty){
		this.beans=beans;
		this.idProperty=idProperty;
		this.pidProperty=pidProperty;
		this.displayProperty=displayProperty;
		
		
	}
	
	/**
	 * 获取符合 id＝pid 的，或者符合pid为String.empt 或者null 的条件的结点为根结点.
	 * @param items
	 * @return
	 */
	private Map<DefaultMutableTreeNode,String []> getRootNode(List<String []> items){
		Map<DefaultMutableTreeNode,String []> map=new HashMap<DefaultMutableTreeNode, String[]>();
		
		for(String [] currItem: items){
			if(currItem[0]==currItem[1]){ //root item condition
				DefaultMutableTreeNode t=  new DefaultMutableTreeNode(currItem[2]);
				t.setUserObject(currItem[2]);
				map.put(t, currItem);
				break;
			}
			if("".equals(currItem[1])){ //or root item condition
				DefaultMutableTreeNode t= new DefaultMutableTreeNode(currItem[2]);
				t.setUserObject(currItem[2]);
				map.put(t, currItem);
				break;
			}
		}
		return map;
	}
	
	private  void  buidTreeNodes(Map<DefaultMutableTreeNode,String []> rootMap, List<String []> dataNodes){
		String [] rootNode=rootMap.values().iterator().next();
		DefaultMutableTreeNode uiRootNode=rootMap.keySet().iterator().next();
		
		//去除根结点（数据）
		Map<String[],DefaultMutableTreeNode> map=new HashMap<String[], DefaultMutableTreeNode>();
		for(String [] node: dataNodes){
			if ( !(rootNode[0].equals(node[0]) && rootNode[1].equals(node[1]))){
				DefaultMutableTreeNode eleNode=new DefaultMutableTreeNode(node[2]);
				map.put(node,  eleNode);
			}
		}
		
		for(String[] row: map.keySet()){
			System.out.println(row[0]+"  "+row[1]+"  "+row[2]);
			
			DefaultMutableTreeNode p=map.get(row);
			for(String [] r: map.keySet()){
				DefaultMutableTreeNode c=map.get(r);
				if(row!=r){
					if(row[1].equals(r[0])){
						c.add(p);
					}
				}
			}
			
			if(rootNode[0].equals(row[1])){
				uiRootNode.add(p);
			}
		}
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
		Field field =clazz.getDeclaredField(property);
		
		boolean isAccess=field.isAccessible();
		
		field.setAccessible(true);
		Object value=field.get(obj);
		field.setAccessible(isAccess);
		
		return value;
	}
	
	
private void build(){
		try {
			
			List<String[]> list= convertBean(beans);

			Map<DefaultMutableTreeNode,String []> rootMap=getRootNode(list);
			DefaultMutableTreeNode dataRootNode = rootMap.keySet().iterator().next(); // find  data  root node
			
			 // 从跟结点开始构建树状结构
			buidTreeNodes(rootMap, list);

			DefaultTreeModel treeModel = new DefaultTreeModel(dataRootNode);
			
			 
			setModel(new Model(treeModel));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(
					"Binding data failed, resulting in reflect get Bean's value ! ==>"
							+ ex.getMessage());
		}
	}

	/**
	 * java bean数据 转化 
	 * @param beans
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private List<String[]> convertBean(List<?> beans) throws NoSuchFieldException,
			IllegalAccessException {
		List<String[]> list = new ArrayList<String[]>();
		
		for (Object i : beans) {
			Object idPropertyValue = getBeanPropertyValue(i, idProperty); // parent
			Object pidPropertyValue = getBeanPropertyValue(i, pidProperty); // child
			Object displayPropertyValue = getBeanPropertyValue(i, displayProperty); // lable
	
			if (null != idPropertyValue && null != pidPropertyValue) {
	
				String[] row = new String[] {
						String.valueOf(idPropertyValue),
						String.valueOf(pidPropertyValue),
						null == displayPropertyValue ? "" : String
								.valueOf(displayPropertyValue) };
	
				list.add(row);
			} else {
				throw new NullPointerException(
						"SimpleTree#build() error, parentId and childId must be not null,the root item must be pid=id !");
			}
		}
		return list;
	}

	@Override
	protected void onInitialize() {
		build();
		super.onInitialize();
		
	}
}
