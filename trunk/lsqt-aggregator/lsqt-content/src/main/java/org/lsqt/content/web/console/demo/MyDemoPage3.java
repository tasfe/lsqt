package org.lsqt.content.web.console.demo;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage3 extends AbstractPage{
	public MyDemoPage3(){
		Form form = new Form( "form");
		
		
		TextField t1=	new TextField( "text",new Model(""));
	
		
		TextField t2=new TextField( "integer", new Model(""),Integer. class);
		t2.setRequired(true);
		form.add(t1);
		form.add(t2);

		add(form);
	}
}
