package com.lsqt.modules.resource.web.console;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		super.init();
		this.getMarkupSettings().setStripWicketTags(true);
	}
	
	public Class<? extends Page> getHomePage() {
		return UserManage.class;
	}
}
