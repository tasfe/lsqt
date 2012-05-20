package org.lsqt.content.web.console.demo;

import java.io.Serializable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
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
				
				MyDemoPage2.this.remove(lblShowName );
				Label lblTemp=new Label("showName","密码明文："+user.getUserPwd());
				MyDemoPage2.this.add(lblTemp);
			}
		};
		form.add(lblShowName);
		form.add(pwd);
		add(form);
	}
}
