package org.lsqt.content.web.wicket;


import org.apache.wicket.Page;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lsqt.content.web.console.demo.MyDemoPage;
import org.springframework.stereotype.Component;
import org.apache.wicket.spring.SpringWebApplicationFactory;

@Component("consoleApplication")
public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		 super.init(); 
		 getComponentInstantiationListeners().add(new SpringComponentInjector(this)); //对于wicket1.5以下的版本应写成addComponentInstantiationListeners(new SpringComponentInjector(this));
		 System.out.println("console application init");
		 
		 SecurePackageResourceGuard guard = (SecurePackageResourceGuard) getResourceSettings().getPackageResourceGuard();
		 guard.addPattern("+*.htm");
	 }
	

	public Class<? extends Page> getHomePage() {
		return UserManage.class;
		//return ConsoleIndex.class;
	}
	
	
}
