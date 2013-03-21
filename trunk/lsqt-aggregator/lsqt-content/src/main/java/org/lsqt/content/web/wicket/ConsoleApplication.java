package org.lsqt.content.web.wicket;

import org.apache.velocity.app.Velocity;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.response.filter.ServerAndClientTimeFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.lsqt.content.web.wicket.content.AppListPage;
/**
 * 
 * @author 袁明敏
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
		 
		 //集成velocity使用 
		 guard.addPattern("+*.vm");
		 try
			{
				Velocity.init();
			}
			catch (Exception e)
			{
				throw new WicketRuntimeException(e);
			}
		 
		 //setRootRequestMapper(new HttpsMapper(getRootRequestMapper(),new HttpsConfig()));
		 
		 //test();
		 
		// WicketSource.configure(this);
	 }
	
	private void addDebugsSetting(){
		getDebugSettings().setDevelopmentUtilitiesEnabled(true);
		getRequestCycleSettings().addResponseFilter(new ServerAndClientTimeFilter());
	}

	public Class<? extends Page> getHomePage() {
		return AppListPage.class;
	}
	
	public void test()
	{
		getAjaxRequestTargetListeners().add(new AjaxRequestTarget.AbstractListener()
		{
			@Override
			public void updateAjaxAttributes(AjaxRequestAttributes attributes)
			{
				super.updateAjaxAttributes(attributes);
				System.out.println(attributes.getDataType());
				System.out.println(attributes.getFormId());
				System.out.println(attributes.getDynamicExtraParameters());
				System.out.println(attributes.getChannel());
				attributes.setChannel(new AjaxChannel("globalAjaxChannel", AjaxChannel.Type.ACTIVE));
			}
		});
	}
}
