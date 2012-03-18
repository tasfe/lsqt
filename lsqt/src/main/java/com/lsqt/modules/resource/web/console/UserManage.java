package com.lsqt.modules.resource.web.console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Component;

import com.lsqt.modules.resource.dao.UserDao;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.service.UserService;

public class UserManage extends WebPage {
	//@SpringBean
	UserService userService;
	
	
	
	/****/
	private static final long serialVersionUID = 1L;
	
	public UserManage(){
		
		  
		
		
		List<String> list=new ArrayList<String>();
		list.add("男");
		list.add("女");
		
		final User  user=new User();
		Form<User> form=new Form<User>("form",new CompoundPropertyModel<User>(user)){
			
			

			/**
			 * 
			 */
			private static final long serialVersionUID = -7465829731566596205L;

			@Override
			protected void onSubmit() {
				System.out.println(user.getEmail());
				
				
				
			}
			
			@Override
			protected void onValidate() {
				super.onValidate();
			}
		};
		
		form.add(new TextField<String>("userId").setType(String.class));
		form.add(new TextField<String>("userName").setType(String.class));
		form.add(new EmailTextField("email").setType(String.class));
		form.add(new TextArea<String>("desc").setType(String.class));
		form.add(new DropDownChoice<String>("sex",list));
		
		//form.add(new PasswordTextField("birthday").setType(Date.class));
		/*
		form.add(new TextField<String>("").setType(Integer.class));
		
		
		
		*/
		/*WebMarkupContainer birthday=new WebMarkupContainer("birthday");
		form.add(birthday);*/
		
		TextField<String> tf=new TextField<String>("birthday");
		tf.setType(Date.class);
		DatePicker dd=new DatePicker();
		//dd.onConfigure(tf);
		dd.setShowOnFieldClick(true);
		//dd.isEnabled(tf);
		//dd.bind(tf);
		form.add(dd);
		form.add(tf);
		
		
		
		
		super.add(form);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
