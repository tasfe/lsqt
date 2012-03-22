package com.lsqt.modules.resource.web.console;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import  org.apache.wicket.Application ;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lsqt.modules.resource.service.UserService;
@Component("consoleApplication")
public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		super.init();
		//this.getMarkupSettings().setStripWicketTags(true);
		
		
		/*
		
		WebApplicationContext app = (WebApplicationContext)super.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		//ApplicationContext app= WebApplicationContextUtils.getWebApplicationContext(super.getServletContext());
		System.out.println("aaaaaaaaaaaaaaaaaaaaaa"+app.getBeanDefinitionNames().length);
		
		
		getComponentInstantiationListeners().add(new SpringComponentInjector(this,app,true)) ;
		//org.apache.wicket.util.lang.Args.notEmpty(collection, name)
*/	}
	

	public Class<? extends Page> getHomePage() {
		return UserManage.class;
	}
	
}
