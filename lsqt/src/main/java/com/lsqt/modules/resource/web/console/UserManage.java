package com.lsqt.modules.resource.web.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;

import com.lsqt.modules.resource.model.User;

public class UserManage extends WebPage {
	/****/
	private static final long serialVersionUID = 1L;
	
	public UserManage(){
		List<String> list=new ArrayList<String>();
		list.add("男");
		list.add("女");
		
		User  user=new User();
		Form<User> form=new Form<User>("form",new CompoundPropertyModel<User>(user)){
			
			

			/**
			 * 
			 */
			private static final long serialVersionUID = -7465829731566596205L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
			}
			
			@Override
			protected void onValidate() {
				super.onValidate();
			}
		};
		
		form.add(new TextField<String>("userId").setType(String.class));
		form.add(new TextField<String>("userName").setType(String.class));
		form.add(new PasswordTextField("birthday").setType(Date.class));
		/*
		form.add(new TextField<String>("").setType(Integer.class));
		form.add(new TextArea<String>("desc").setType(String.class));
		form.add(new EmailTextField("email").setType(String.class));
		form.add(new DropDownChoice<String>("sex",list));
		*/
		/*WebMarkupContainer birthday=new WebMarkupContainer("birthday");
		form.add(birthday);*/
		add(form);
		
		
		
		
		super.add(form);
	}
}
