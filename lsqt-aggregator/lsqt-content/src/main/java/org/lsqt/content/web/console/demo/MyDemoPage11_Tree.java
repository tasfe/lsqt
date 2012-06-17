package org.lsqt.content.web.console.demo;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.link.Link;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage11_Tree extends AbstractPage {
	
	public MyDemoPage11_Tree(){
		super();
		
		final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		rootNode.setUserObject(this);
		
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
				if (node.getUserObject( ) == MyDemoPage11_Tree. this) {
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
