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
import org.lsqt.content.web.console.demo.AjaxDemo_AutoCompleteTextField;
import org.lsqt.content.web.console.demo.AjaxDemo_DropDownChoice;
import org.lsqt.content.web.console.demo.AjaxDemo_ExternalLink;
import org.lsqt.content.web.console.demo.AjaxDemo_Link;
import org.lsqt.content.web.console.demo.EditorDemo;
import org.lsqt.content.web.console.demo.MyDemoPage;
import org.lsqt.content.web.console.demo.MyDemoPage10;
import org.lsqt.content.web.console.demo.MyDemoPage11_Tree;
import org.lsqt.content.web.console.demo.MyDemoPage2;
import org.lsqt.content.web.console.demo.MyDemoPage3;
import org.lsqt.content.web.console.demo.MyDemoPage5;
import org.lsqt.content.web.console.demo.MyDemoPage6;
import org.lsqt.content.web.console.demo.MyDemoPage7;
import org.lsqt.content.web.console.demo.MyDemoPage8;
import org.lsqt.content.web.console.demo.MyDemoPage9;
import org.lsqt.content.web.console.demo.MyDemoTabPanel;
import org.lsqt.content.web.console.demo.MyTest_DropDownChoice;
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
		
		BookmarkablePageLink<MyDemoPage5> book5=new BookmarkablePageLink<MyDemoPage5>("checkboxDemo", MyDemoPage5.class);
		form.add(book5);
		
		BookmarkablePageLink<MyDemoPage6> book6=new BookmarkablePageLink<MyDemoPage6>("dropList", MyDemoPage6.class);
		form.add(book6);
		
		BookmarkablePageLink<MyDemoPage7> book7=new BookmarkablePageLink<MyDemoPage7>("demo7", MyDemoPage7.class);
		form.add(book7);
		
		BookmarkablePageLink<MyDemoPage8> book8=new BookmarkablePageLink<MyDemoPage8>("demo8", MyDemoPage8.class);
		form.add(book8);
		
		BookmarkablePageLink<MyDemoPage9> book9=new BookmarkablePageLink<MyDemoPage9>("demo9", MyDemoPage9.class);
		form.add(book9);
		
		BookmarkablePageLink<MyDemoPage10> book10=new BookmarkablePageLink<MyDemoPage10>("demo10", MyDemoPage10.class);
		form.add(book10);
		
		BookmarkablePageLink<EditorDemo> book11=new BookmarkablePageLink<EditorDemo>("demo11", EditorDemo.class);
		form.add(book11);
		
		BookmarkablePageLink<EditorDemo> book12=new BookmarkablePageLink<EditorDemo>("demo12", MyDemoPage11_Tree.class);
		form.add(book12);
		
		//-------------------Ajax demo---------------
		BookmarkablePageLink<AjaxDemo_Link> ajaxLink=new BookmarkablePageLink<AjaxDemo_Link>("AjaxDemo_Link", AjaxDemo_Link.class);
		form.add(ajaxLink);
		
		BookmarkablePageLink<AjaxDemo_ExternalLink> ajaxLink2=new BookmarkablePageLink<AjaxDemo_ExternalLink>("AjaxDemo_Link_validate", AjaxDemo_ExternalLink.class);
		form.add(ajaxLink2);
		
		BookmarkablePageLink<AjaxDemo_DropDownChoice> ajaxSelect3=new BookmarkablePageLink<AjaxDemo_DropDownChoice>("ajaxSelect3", AjaxDemo_DropDownChoice.class);
		form.add(ajaxSelect3);
		
		BookmarkablePageLink<AjaxDemo_AutoCompleteTextField> comp=new BookmarkablePageLink<AjaxDemo_AutoCompleteTextField>("tttttt", AjaxDemo_AutoCompleteTextField.class);
		form.add(comp);
		
		//------------------my test ------------------------
		BookmarkablePageLink<MyTest_DropDownChoice> t22=new BookmarkablePageLink<MyTest_DropDownChoice>("t22", MyTest_DropDownChoice.class);
		form.add(t22);
		
		BookmarkablePageLink<MyDemoTabPanel> tab=new BookmarkablePageLink<MyDemoTabPanel>("tab", MyDemoTabPanel.class);
		form.add(tab);
		
		
		
		//------------------------------------------------------------
	}
}
