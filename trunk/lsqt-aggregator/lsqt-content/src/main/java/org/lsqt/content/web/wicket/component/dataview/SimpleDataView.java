package org.lsqt.content.web.wicket.component.dataview;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.lsqt.content.model.User;
import org.lsqt.content.web.wicket.AbstractPage;

public class SimpleDataView extends AbstractPage {
	public SimpleDataView(){
		List<User> list=new ArrayList<User>();
		for(int i=0;i<100;i++){
			User u=new User();
			u.setId("ID_"+i);
			u.setEmail("keke"+i+"@sohu.com");
			u.setUserPwd("pwd"+i);
			u.setUserId("wang "+i);
			list.add(u);
		}
		
		DataView dataView=new DataView("",new ListDataProvider(list),20) {
			protected void populateItem(Item item) {
				User u=(User)item.getModelObject();
				
			}
		} ;
	}
}
