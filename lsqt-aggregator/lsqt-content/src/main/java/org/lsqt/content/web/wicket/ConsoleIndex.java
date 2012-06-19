package org.lsqt.content.web.wicket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.extensions.markup.html.tree.Tree;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;

import org.lsqt.content.web.wicket.component.gridview.SimpleGridView;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;

public class ConsoleIndex extends AbstractPage {

	public ConsoleIndex() {
		//初使化功能树状结构
		SimpleTree tree = new SimpleTree("tree");
		add(tree);

		
		SimpleGridView gridView=new SimpleGridView("gridView");
		add(gridView);
		
	}


}
