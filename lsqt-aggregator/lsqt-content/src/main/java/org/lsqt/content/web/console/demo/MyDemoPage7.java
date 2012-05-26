package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.lsqt.content.model.User;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage7 extends AbstractPage{
	public MyDemoPage7(){
		final Map<String,String> map=new HashMap<String,String>();
		map.put("sohu", "http://sohu.com");
		map.put("yahoo", "http://yahoo.com.cn");
		map.put("sina","http://sina.com");
		
		final List<String> myList=new ArrayList<String>(map.keySet());
		ListView<String> list=new ListView<String>("items",myList){
			private static final long serialVersionUID = -6335196435541993364L;

			protected void populateItem(ListItem<String> item) {
				String key=item.getModelObject();
				item.add(new Label("key",key));
				ExternalLink link=	new ExternalLink("link",map.get(key));
				
				item.add(link);
				
				Label temp=new Label("value",map.get(key));
				link.add(temp);
			};
		} ;
		add(list);
		
		
		//假分页
		List<User> userList=new ArrayList<User>();
		for(int i=0;i<100;i++){
			User u=new User();
			u.setEmail("test"+i+"@sohu.com");
			u.setUserId("张"+i);
			u.setUserPwd("XXXX"+i);
			userList.add(u);
		}
		PageableListView<User> page=new PageableListView<User>("list",userList,20){
			protected void populateItem(ListItem<User> item) {
				User user=item.getModelObject();
				item.add(new Label("id",user.getUserId()));
				item.add(new Label("userPwd",user.getUserPwd()));
				item.add(new Label("userEmail",user.getEmail()));
			}
		};
		add(new PagingNavigator("navigator",page));
		add(page);
		
		
		//扩展控件，日历组件
		DateTextField df = new DateTextField("dateTxt", "yyyy-MM-dd");
		df.add(new DatePicker());
		add(df);
	}
}
