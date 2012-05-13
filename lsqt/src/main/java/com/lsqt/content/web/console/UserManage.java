package com.lsqt.content.web.console;

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
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lsqt.content.dao.UserDao;
import com.lsqt.content.model.User;
import com.lsqt.content.service.UserService;
import com.lsqt.content.service.UserServiceImpl;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.markup.html.WebComponent;

public class UserManage extends AbstractPage {
	/****/
	private static final long serialVersionUID = 1L;
	
	public UserManage(){

		List<String> list=new ArrayList<String>();
		list.add("1");
		list.add("0");
		
		final User  user=new User();
		Form<User> form=new Form<User>("form",new CompoundPropertyModel<User>(user)){
			private static final long serialVersionUID = -7465829731566596205L;
			protected void onSubmit() {
				userService.save(user);
			}
		};
		
		form.add(new TextField<String>("userId").setType(String.class));
		form.add(new EmailTextField("email").setType(String.class));
		super.add(form);
	}

}
