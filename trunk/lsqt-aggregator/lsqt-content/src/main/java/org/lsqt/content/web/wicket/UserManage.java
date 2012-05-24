package org.lsqt.content.web.wicket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.dao.UserDao;
import org.lsqt.content.model.User;
import org.lsqt.content.service.UserService;
import org.lsqt.content.service.UserServiceImpl;
import org.lsqt.content.web.console.demo.MyDemoPage;
import org.lsqt.content.web.console.demo.MyDemoPage2;
import org.lsqt.content.web.console.demo.MyDemoPage3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.markup.html.WebComponent;

public class UserManage extends AbstractPage {
	/****/
	private static final long serialVersionUID = 1L;

	private int count = 0;

	public UserManage() {

		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("0");

		final User user = new User();
		Form<User> form = new Form<User>("form",
				new CompoundPropertyModel<User>(user)) {
			private static final long serialVersionUID = -7465829731566596205L;

			protected void onSubmit() {
				userService.save(user);
			}
		};

		form.add(new TextField<String>("userId").setType(String.class));
		form.add(new EmailTextField("email").setType(String.class));
		form.add(new PasswordTextField("userPwd").setType(String.class));

		Link link = new Link<Object>("link") {
			public void onClick() {
				count++;
			}
		};

		link.add(new Label("count", new Model() {
			public Object getObject(Component component) {
				return Integer.toString(count);
			}
		}));

		form.add(link);
		form.add(new ExternalLink( "externalLink", "http://www.sina.com.cn", "新浪"));
		super.add(form);
		form.add(new ExternalLink( "externalLink2",new Model(){
			
			public Serializable getObject() {
				return String.valueOf("http://sohu.com");
			}
		},new Model()) {
			
			public Serializable getObject() {
				return String.valueOf("搜狐");
			}
		});
		
		//从用户管理页面，跳转到demo页面
		BookmarkablePageLink<MyDemoPage> bookLink=new BookmarkablePageLink<MyDemoPage>("bookLink", MyDemoPage.class);
		form.add(bookLink);
		
		//demo2页面，跳转
		BookmarkablePageLink<MyDemoPage2> myDemoPage2=new BookmarkablePageLink<MyDemoPage2>("myDemoPage2", MyDemoPage2.class);
		form.add(myDemoPage2);
		
		//表单输入控件页，跳转
		BookmarkablePageLink<MyDemoPage3> myDemoPage3=new BookmarkablePageLink<MyDemoPage3>("toPage3", MyDemoPage3.class);
		form.add(myDemoPage3 );
	}
}