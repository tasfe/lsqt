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

import org.lsqt.content.web.wicket.component.SimpleTree;

public class ConsoleIndex extends AbstractPage {
	private static List counts = new ArrayList();
	static {
		for (int i = 0; i < 40; i++) {
			counts.add(new Integer(i));
		}
	}

	
	public ConsoleIndex() {
		super();
		SimpleTree tree = new SimpleTree("tree");
		add(tree);

		// -----------------------------------------------
		IDataProvider dataProvider = new ListDataProvider(counts);
		GridView gridView = new GridView("rows", dataProvider) {
			protected void populateItem(Item item) {
				final Integer integer = (Integer) item.getModelObject();
				item.add(new Image("static", "images/" + integer + ".gif"));
				item.add(new Label("label", "功能" + integer));
			}

			protected void populateEmptyItem(Item item) {
				Image image = new Image("static", "images/null.gif");
				image.setVisible(false);
				item.add(image);
				item.add(new Label("label", ""));
				// 因为没有图片,所以将图片隐藏起来
			}
		};
		gridView.setRows(10);
		gridView.setColumns(5);
		// 3列4行  
		add(gridView);
	}


}
