package org.lsqt.content.web.wicket.component.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.LinkTree;

/**
 * <pre>
 * 
 * SimpleTree tree = new SimpleTree(&quot;simpleTree&quot;);
 * tree.bindData(list, &quot;id&quot;, &quot;pid&quot;, &quot;name&quot;, &quot;description&quot;);
 * add(tree);
 * </pre>
 * 
 * @author mm
 * 
 */
public class SimpleTree extends Panel {
	private static final long serialVersionUID = 1L;

	
	private List<?> beans;
	private String idProperty;
	private String pidProperty;
	private String displayProperty;
	private String urlProperty;
	
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
	 * 绑定实体对象到树，指定树控件要显示的Bean父子级属性和Label属性和点击页面时跳转的URL属性.
	 * @param beans
	 * @param idProperty
	 * @param pidProperty
	 * @param displayProperty
	 * @param urlProperty
	 */
	public void bindData(List<?> beans,String idProperty, String pidProperty,String displayProperty,String urlProperty){
		bindData( beans, idProperty, pidProperty,displayProperty);
		this.urlProperty=urlProperty;
		
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
	
	/**
	 * 构建结点,返回结点与结点上的数据映射.
	 * @param rootMap
	 * @param dataNodes
	 * @return
	 */
	private  Map<DefaultMutableTreeNode,String[]>  buidTreeNodes(Map<DefaultMutableTreeNode,String []> rootMap, List<String []> dataNodes){
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
		//	System.out.println(row[0]+"  "+row[1]+"  "+row[2]+" "+row[3]);
			
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
		
		//重新构造整棵树结点，并返回“结点上的数据(且包含根结点)”
		LinkedHashMap<DefaultMutableTreeNode, String[]> treeNodeMap=new LinkedHashMap<DefaultMutableTreeNode, String[]>(map.size()+1);
		treeNodeMap.put(uiRootNode, rootNode);
		
		Set<Entry<String[], DefaultMutableTreeNode>> set=map.entrySet();
		for(Entry<String[], DefaultMutableTreeNode> e: set){
			treeNodeMap.put(e.getValue(), e.getKey());
		}
		return treeNodeMap;
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
	@SuppressWarnings("rawtypes")
	private static Object getBeanPropertyValue(Object obj,String property) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		Class clazz=obj.getClass();
		Field field =clazz.getDeclaredField(property);
		
		boolean isAccess=field.isAccessible();
		
		field.setAccessible(true);
		Object value=field.get(obj);
		field.setAccessible(isAccess);
		
		//System.out.println(property+" : "+value);
		return value;
	}
	
	
	public SimpleTree(String id){
		super(id);
	}

	private void build(){

		
		try {
			
			List<String[]> list= convertBean(beans);

			Map<DefaultMutableTreeNode,String []> rootMap=getRootNode(list);
			DefaultMutableTreeNode dataRootNode = rootMap.keySet().iterator().next(); // find  data  root node
			
			 // 从跟结点开始构建树状结构
			final Map<DefaultMutableTreeNode,String []> treeNodesMap=buidTreeNodes(rootMap, list);

			DefaultTreeModel treeModel = new DefaultTreeModel(dataRootNode);
			
			final LinkTree tree = new LinkTree("tree", treeModel){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@Override
				protected void onNodeLinkClicked(Object node, BaseTree tree,
						AjaxRequestTarget target) {
					super.onNodeLinkClicked(node, tree, target);
					if (getTreeState().isNodeExpanded(node)) {
						getTreeState().collapseNode(node);
					}else{
						getTreeState().expandNode(node);
					}
					
					
					try {
						String[] nodeData= treeNodesMap.get(node);
						if(StringUtils.isNotBlank(nodeData[3] )){
							Class page = Class.forName(nodeData[3]);
							setResponsePage(page );
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				@Override
				protected void onJunctionLinkClicked(AjaxRequestTarget target,
						Object node) {
					super.onJunctionLinkClicked(target, node);
					DefaultMutableTreeNode ele=(DefaultMutableTreeNode)node;
					
					getTreeState().expandNode(ele);
					
					
					 
					
				}
				
			};
			add(tree);

			add(new AjaxLink<Void>("expandAll") {
				/**   */
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					tree.getTreeState().expandAll();
					target.add(tree);
					
					
				}
			});

			add(new AjaxLink<Void>("collapseAll") {
				/**   */
				private static final long serialVersionUID = 1L;

				public void onClick(AjaxRequestTarget target) {
					tree.getTreeState().collapseAll();
					target.add(tree);
					
					
				}
			});
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
			Object urlPropertyValue=getBeanPropertyValue(i, urlProperty); // url
			if (null != idPropertyValue && null != pidPropertyValue) {

				String[] row = new String[] {
						String.valueOf(idPropertyValue),
						String.valueOf(pidPropertyValue),
						null == displayPropertyValue ? "" : String.valueOf(displayPropertyValue),
						null == urlPropertyValue ? "" : String.valueOf(urlPropertyValue)
				};

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
		super.onInitialize();
		build();
	}
}
