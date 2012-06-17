package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage4  extends AbstractPage{
	public MyDemoPage4(){
		//DataTable<String> a=null;
		
		TextArea textArea=new TextArea("textArea");
		add(textArea);
		
		CheckBox chk=new CheckBox("mychk",new Model("test"));
		add(chk);
		
		
		List<String> list=Arrays.asList(new String[]{"张三","李四","王五"});
		CheckBoxMultipleChoice  chks=new CheckBoxMultipleChoice("myCheckboxGroup", list);
		
		add(chks);
		
		final Map<String,String> map=new java.util.HashMap<String,String>();
		map.put("sina","新浪");
		map.put("sohu","搜狐");
		map.put("qq","腾讯");
		IChoiceRenderer<String> choice= new ChoiceRenderer<String>(){
			public Object getDisplayValue(String object) {
				return map.get(object);
			}
		};
		CheckBoxMultipleChoice  chks2=new CheckBoxMultipleChoice("myCheckboxGroup2",new ArrayList( map.keySet()),choice);
		add(chks2);
	}
}
