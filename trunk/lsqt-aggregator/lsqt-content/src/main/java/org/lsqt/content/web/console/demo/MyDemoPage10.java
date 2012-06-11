package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;

import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage10 extends AbstractPage{
	public MyDemoPage10(){
		Form form=new Form("form");
		add(form);
		
		//账号
		TextField account=	new TextField( "account",new Model(""));
		account.setRequired(true);
		form.add(account);

		// name
		TextField<String> name = new TextField<String>("name");
		form.add(name);

		// 密码
		PasswordTextField password = new PasswordTextField("password");
		form.add(password);
		
		// 性别
		final Map<Boolean, String> femaleMap = new HashMap<Boolean, String>();
		femaleMap.put(Boolean.TRUE, "男");
		femaleMap.put(Boolean.FALSE, "女");
		IChoiceRenderer render = new ChoiceRenderer() {
			public Object getDisplayValue(Object object) {
				System.out.println(object.getClass().getName()+"======");
				return femaleMap.get(object);
			}
		};
		RadioChoice<String> female = new RadioChoice<String>("female",new ArrayList(femaleMap.keySet()), render);
		form.add(female);
		
		// 生日
		DateTextField dtf = new DateTextField("birthday", "yyyy-MM-dd");
		dtf.add(new DatePicker());
		form.	add(dtf);
		
		// 薪水
		NumberTextField<Double> salary = new NumberTextField<Double>("salary");
		form.add(salary);
		
		// 职位
		CheckGroup group=new CheckGroup("job");
		form.add(group);
	//	ListView listView=new ListView("");
		
		//工龄
		NumberTextField<Double> workYear=new NumberTextField<Double>("workYear");
		form.add(workYear);
		
		//爱好
		
	}
}
