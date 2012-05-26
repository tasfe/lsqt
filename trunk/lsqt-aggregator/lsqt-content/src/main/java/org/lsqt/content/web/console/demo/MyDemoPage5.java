package org.lsqt.content.web.console.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage5 extends AbstractPage{
	private static List SITES = new ArrayList();
	private static Map NAMES = new HashMap();

	static {
		SITES.add("http://www.sina.com.cn");
		SITES.add("http://www.sohu.com");
		SITES.add("http://www.163.com" );
		
		NAMES.put("http://www.sina.com.cn", "新浪");
		NAMES.put("http://www.sohu.com", "搜虎");
		NAMES.put("http://www.163.com", "网易");
	}
	
	public MyDemoPage5() {
		Form form = new Form("form");
		add(form);

		CheckGroup group = new CheckGroup("group", new ArrayList());
		form.add(group);
		group.add(new CheckGroupSelector("groupSelector"));

		ListView sites = new ListView("sites", SITES) {
			
			protected void populateItem(ListItem item) { //填充数据项
				Object object = item.getModelObject();
				item.add(new Check("check", new Model(Integer.toString(item.getIndex()))));
				item.add(new Label("name", new Model((Serializable) NAMES.get(object))));
			};
		};
		group.add(sites);
	}
}
