package org.lsqt.content.web.console.demo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage6 extends AbstractPage{
	public MyDemoPage6(){
		final Map<String,String> map=new HashMap<String,String>();
		map.put("key1",  "The Server Side1............");
		map.put("key2",  "The Server Side2.......................");
		map.put("key3",  "The Server Side3...................................");
		
		IChoiceRenderer<String> render=new ChoiceRenderer<String>(){
			public Object getDisplayValue(String object) {
				return map.get(object);
			}
		};
		
		List SITE=new ArrayList<String>(map.keySet());
		DropDownChoice down=new DropDownChoice("dropDwonList",SITE );  //下拉列表
		/*
		down.setRequired(true);
		down.setNullValid(true);
		*/
		down.setChoiceRenderer(render);
		down.setEnabled(true);
		add(down);
		
		ListChoice temp1=new ListChoice("temp1",SITE);
		/*IModel model=new Model();
		model.setObject(SITE);*/
		temp1.setChoiceRenderer(render);
		add(temp1);
		
		ListMultipleChoice temp2=new ListMultipleChoice("temp2",SITE);
		temp2.setChoiceRenderer(render);
		add(temp2);
	}

}
