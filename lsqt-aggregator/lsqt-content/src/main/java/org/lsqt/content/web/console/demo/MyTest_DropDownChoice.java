package org.lsqt.content.web.console.demo;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyTest_DropDownChoice extends AbstractPage {
	public MyTest_DropDownChoice(){
		List<String> names=new LinkedList<String>();
		names.add("张三");
		names.add("李四");
		names.add("王五");
		names.add("赵六");
		
		DropDownChoice<String> choice=new DropDownChoice("names",names);
		add(choice);
	}
	
	
}
