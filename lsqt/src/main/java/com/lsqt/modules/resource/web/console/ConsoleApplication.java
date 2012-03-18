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

public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		super.init();
		this.getMarkupSettings().setStripWicketTags(true);
		//getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		//org.apache.wicket.util.lang.Args.notEmpty(collection, name)
	}
	
	public Class<? extends Page> getHomePage() {
		return UserManage.class;
	}
	
}
