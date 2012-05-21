package org.lsqt.content.web.console.demo;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.lsqt.content.model.User;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage2 extends AbstractPage{
	public MyDemoPage2(){
		
		PasswordTextField pwd=new PasswordTextField("userPwd");
		final Label lblShowName=new Label("showName","密码明文：");
		
		
		final User user = new User();
		Form form=new Form("form",new CompoundPropertyModel<User>(user)){
			protected void onSubmit() {
				/*lblShowName.setDefaultModelObject(new Model(){
					public Serializable getObject() {
						
						return user.getUserPwd();
					}
				});*/
				
			/*
			 * 	MyDemoPage2.this.remove(lblShowName );
				Label lblTemp=new Label("showName","密码明文："+user.getUserPwd());
				MyDemoPage2.this.add(lblTemp);
				*/
				info("用户密码明文为："+user.getUserPwd());
				System.out.println("0000000");
			}
		};
		
		Button btn=new Button("btn"){
			public void onSubmit() {
				System.out.println("11111111");
			}
		};
		form.add(btn);
		
		Button btn2=new Button("btn2"){
			public void onSubmit() {
				System.out.println("22222");
			}
		};
		form.add(btn2);
		
		Button btn3=new Button("btn3"){
			public void onSubmit() {
				System.out.println("33333");
			}
		};
		btn3.setDefaultFormProcessing(false);
		form.add(btn3);
		
		
		//页面上的提示信息
		FeedbackPanel panel=new FeedbackPanel("feedback");
		form.add(panel);
		
		
		form.add(lblShowName);
		form.add(pwd);
		add(form);
		
		
	}
}
