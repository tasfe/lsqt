package org.lsqt.content.web.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.service.UserService;


@SuppressWarnings("serial")
public abstract class AbstractPage extends WebPage {
	
	
 
	
	@SpringBean(name="userServiceImpl")
	protected UserService userService ;
	
	@SpringBean(name="newsServiceImpl")
	protected NewsService newsService;
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
