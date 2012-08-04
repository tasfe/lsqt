package org.lsqt.content.web.wicket;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.lsqt.content.model.News;
import org.lsqt.content.model.User;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;

import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;
import org.lsqt.content.web.wicket.component.gridview.SimpleGridView;
import org.lsqt.content.web.wicket.component.tree.Tree;

public class ConsoleIndex extends AbstractPage {

	public ConsoleIndex() {
		
		newsService.save(null);
		
		//初使化功能树状结构
		Tree tree = new Tree("my_tree");
		add(tree);
   
		
		SimpleGridView gridView=new SimpleGridView("gridView");
		add(gridView);
		
		List<User> users=new ArrayList<User>();
		for(int i=0;i<100;i++){
			User u=new User();
			u.setId(i+"");
			u.setUserId("userId"+i);
			u.setUserPwd("userPwd"+i);
			users.add(u);
		}
		
		SimpleDataTable dt=new SimpleDataTable("simpleDataTable");
		dt.bindData(users,10).displayOn(new String[] { "id", "userId", "userPwd" });
		
		add(dt);
	}


}
