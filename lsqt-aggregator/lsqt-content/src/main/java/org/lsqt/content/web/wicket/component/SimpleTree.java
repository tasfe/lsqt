package org.lsqt.content.web.wicket.component;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;

public class SimpleTree extends Panel {
	
	public SimpleTree(String id){
		super(id);
		final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		rootNode.setUserObject(SimpleTree.this);
		
		
		for (int i = 0; i < 5; i++) {
			DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("parent-" + i);
			for (int j = 0; j < 5; j++) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("child-" + j);
				parentNode.add(childNode);
			}
			rootNode.add(parentNode);
		}
			
		// 构建一个Model
		DefaultTreeModel treeModel = new DefaultTreeModel( rootNode);
		final Tree tree = new Tree( "tree", treeModel) {
			protected String getNodeLabel(DefaultMutableTreeNode node) {
				if(node.isRoot()) {
					return "root";
				}else{
					return node.toString();
				}
				// 重载getNodeLabel方法以显示相应的字符串
			}
		};
		
		add(tree);
		add(new Link("expandAll") {
			public void onClick() {
				tree.getTreeState().expandAll();
				// 展开根结点
			}
		});
		
		add(new Link("collapseAll") {
			public void onClick() {
				tree.getTreeState().collapseAll();
				// 收缩根结点
			}
		});
	}
}
