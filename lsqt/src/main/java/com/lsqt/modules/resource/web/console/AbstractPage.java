package com.lsqt.modules.resource.web.console;

import org.apache.wicket.markup.html.WebPage;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import com.lsqt.modules.resource.service.UserService;

@SuppressWarnings("serial")
public abstract class AbstractPage extends WebPage {
	private ApplicationContext springApp;
	
	
	
	protected <T> T getService(Class<T> requiredType) {
		ConsoleApplication wikiApplication = (ConsoleApplication) getApplication();
		
		WebApplicationContext springApp = (WebApplicationContext) wikiApplication
				.getServletContext()
				.getAttribute(
						WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return springApp.getBean(requiredType);
	}
	
}
