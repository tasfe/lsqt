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
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;
import org.lsqt.content.model.User;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;

import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;
import org.lsqt.content.web.wicket.component.gridview.SimpleGridView;
import org.lsqt.content.web.wicket.component.tree.MyTree;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;

public class ConsoleIndex extends AbstractPage {

	public ConsoleIndex() {
		List<Category> list=new ArrayList<Category>();
		Category c=new Category();
		c.setId("2");
		c.setPid("2");
		c.setDescription("org.lsqt.content.web.wicket.ConsoleIndex");
		c.setName("系统管理");
		
		Category c2=new Category();
		c2.setId("1");
		c2.setPid("2");
		c2.setName("新闻管理");
		c2.setDescription("org.lsqt.content.web.wicket.content.NewsContentPage");
		
		Category c3=new Category();
		c3.setId("3");
		c3.setPid("1");
		c3.setName("系统类别");
		c3.setDescription("org.lsqt.content.web.wicket.content.CategoryPage");
		
		Category c4=new Category();
		c4.setId("9");
		c4.setPid("1");
		c4.setName("c");
		
		Category c5=new Category();
		c5.setId("13");
		c5.setPid("3");
		c5.setName("d");
		
		Category c6=new Category();
		c6.setId("15");
		c6.setPid("9");
		c6.setName("e");
		
		Category c7=new Category();
		c7.setId("0");
		c7.setPid("15");
		c7.setName("f");
		
		Category c8=new Category();
		c8.setId("99");
		c8.setPid("9");
		c8.setName("g");
		
		list.add(c);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
		list.add(c6);
		list.add(c7);
		list.add(c8);
		
		/*String [] rootNode=new String []{"2","2","z"};
		
		List<String[]> list=new ArrayList<String[]>();
		String [] rootNode1=new String []{"1","2","a"};
		String [] rootNode2=new String []{"3","1","b"};
		String [] rootNode3=new String []{"9","1","c"};
		String [] rootNode4=new String []{"13","3","d"};
		String [] rootNode5=new String []{"15","9","e"};
		String [] rootNode6=new String []{"0","15","f"};
		String [] rootNode7=new String []{"99","9","g"};
		list.add(rootNode7);
		list.add(rootNode6);
		list.add(rootNode5);
		list.add(rootNode4);
		list.add(rootNode3);
		list.add(rootNode2);
		list.add(rootNode1);
		list.add(rootNode);*/
		
	
		
		
		
		//初使化功能树状结构
		//MyTree  tree= new MyTree("simpleTree");
		SimpleTree tree=new SimpleTree("simpleTree");
		tree.bindData(list, "id", "pid", "name","description") ;
		add(tree);
   
		
	/*	SimpleGridView gridView=new SimpleGridView("gridView");
		add(gridView);*/
		
		List<User> users=new ArrayList<User>();
		for(int i=0;i<100;i++){
			User u=new User();
			u.setId(i+"");
			u.setUserId("userId"+i);
			u.setUserPwd("userPwd"+i);
			users.add(u);
		}
		
	/*	SimpleDataTable dt=new SimpleDataTable("simpleDataTable");
		dt.bindData(users,10);
		add(dt);
		*/
		
	
	}


}

