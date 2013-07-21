package org.lsqt.content.web.wicket.component.menu.TopMenu;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;

/**
 * 顶部二级菜单,只支持二级显示.
 * @author 袁明敏
 *
 */
public class SecondaryMenu extends Panel
{

	public SecondaryMenu(String id)
	{
		super(id);
	}

	private static final ResourceReference JAVASCRIPT = new JavaScriptResourceReference(
			SecondaryMenu.class, "res/js/lanrenzhijia.js");

		private static final ResourceReference CSS = new CssResourceReference(SecondaryMenu.class,
			"res/css/lanrenzhijia.css");
	
		
	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		CoreLibrariesContributor.contributeAjax(getApplication(), response);
		response.render(JavaScriptHeaderItem.forReference(JAVASCRIPT));

		ResourceReference cssResource = newCssResource();
		if (cssResource != null)
		{
			response.render(CssHeaderItem.forReference(cssResource));
		}
	}

	/**
	 * Allows to override CSS contribution. Returning null means the CSS will be contributed via
	 * other sources, e.g. a global CSS resource.
	 * 
	 * @return The CSS resource reference or null if CSS is contributed via other means.
	 * @see #setCssClassName(String)
	 */
	protected ResourceReference newCssResource()
	{
		return CSS;
	}
}
