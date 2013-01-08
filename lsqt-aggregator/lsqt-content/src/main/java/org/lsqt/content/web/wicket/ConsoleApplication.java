package org.lsqt.content.web.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.https.HttpsConfig;
import org.apache.wicket.protocol.https.HttpsMapper;
import org.apache.wicket.response.filter.ServerAndClientTimeFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lsqt.content.web.wicket.content.NewsAddPage;
/**
 * 
 * @author mm
 *
 */
public class ConsoleApplication extends WebApplication {
	@Override
	protected void init() {
		
		 super.init(); 
		 getComponentInstantiationListeners().add(new SpringComponentInjector(this)); //对于wicket1.5以下的版本应写成addComponentInstantiationListeners(new SpringComponentInjector(this));
		 
		 //web编辑器使用
		 SecurePackageResourceGuard guard = (SecurePackageResourceGuard) getResourceSettings().getPackageResourceGuard();
		 guard.addPattern("+*.htm");
		
		 
		 
		 addDebugsSetting();
		 
		 //setRootRequestMapper(new HttpsMapper(getRootRequestMapper(),new HttpsConfig()));
	 }
	
	private void addDebugsSetting(){
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		getRequestCycleSettings().addResponseFilter(new ServerAndClientTimeFilter());
	}

	public Class<? extends Page> getHomePage() {
		return NewsAddPage.class;
	}
	
	
}
