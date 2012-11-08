package org.lsqt.content.web.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.tree.ITreeState;


@SuppressWarnings("serial")
public abstract class AbstractPage extends WebPage {
	
	
 
	
	@SpringBean(name="userServiceImpl")
	protected UserService userService ;
	
	@SpringBean(name="newsServiceImpl")
	protected NewsService newsService;
	
	
	protected ITreeState state;
	/*
	private ApplicationContext springApp;
	protected <T> T getService(Class<T> requiredType) {
		ConsoleApplication wikiApplication = (ConsoleApplication) getApplication();
		
		WebApplicationContext springApp = (WebApplicationContext) wikiApplication
				.getServletContext()
				.getAttribute(
						WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return springApp.getBean(requiredType);
	}*/
	
}