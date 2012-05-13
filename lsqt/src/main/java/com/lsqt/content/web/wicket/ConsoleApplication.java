package com.lsqt.content.web.wicket;


import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.apache.wicket.spring.SpringWebApplicationFactory;

import com.lsqt.content.web.wicket.demo.MyDemoPage;
@Component("consoleApplication")
public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		 super.init(); 
		 getComponentInstantiationListeners().add(new SpringComponentInjector(this)); //对于wicket1.5以下的版本应写成addComponentInstantiationListeners(new SpringComponentInjector(this));

	 }
	

	public Class<? extends Page> getHomePage() {
		//return UserManage.class;
		return MyDemoPage.class;
	}
	
	
}
