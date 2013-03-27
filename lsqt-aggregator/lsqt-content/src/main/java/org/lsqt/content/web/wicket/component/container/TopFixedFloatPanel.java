package org.lsqt.content.web.wicket.component.container;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.CoreLibrariesContributor;

/**
 * 顶部固定层组件(顶部固定层，随着滚动条下拉使终出现在顶部).
 * 
 * @author 袁明敏
 *
 */
public class TopFixedFloatPanel extends Panel
{	
	/** the default id of the content component */
	public static final String CONTENT_ID = "content";
	
	/*顶部固定层组件需要引用的样式文件*/
	private static final ResourceReference CSS = new CssResourceReference(
			TopFixedFloatPanel.class, "res/TopFixedFloatPanel.css");

	@Override
	public void renderHead(final IHeaderResponse response)
	{
		super.renderHead(response);

		CoreLibrariesContributor.contributeAjax(getApplication(), response);
		// response.render(JavaScriptHeaderItem.forReference(JAVASCRIPT));

		ResourceReference cssResource = newCssResource();
		if (cssResource != null)
		{
			response.render(CssHeaderItem.forReference(cssResource));
		}
	}

	/**
	 * Allows to override CSS contribution. Returning null means the CSS will be
	 * contributed via other sources, e.g. a global CSS resource.
	 * 
	 * @return The CSS resource reference or null if CSS is contributed via
	 *         other means.
	 * @see #setCssClassName(String)
	 */
	protected ResourceReference newCssResource()
	{
		return CSS;
	}
		
	public String getContentId()
	{
		return CONTENT_ID;
	}
	
	/** empty container - used when no component is added */
	private WebMarkupContainer empty;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public TopFixedFloatPanel(String id)
	{
		super(id);
		add(empty = new WebMarkupContainer(getContentId()));
	}
	
	/**
	 * 设置浮动内容.
	 * @param component
	 * @return
	 */
	public TopFixedFloatPanel setContent(final Component component){
		if (component.getId().equals(getContentId()) == false)
		{
			throw new WicketRuntimeException("TopFixedFloatPanel‘s content id is wrong. Component ID:" +
				component.getId() + "; content ID: " + getContentId());
		}
		
		else if (component instanceof AbstractRepeater)
		{
			throw new WicketRuntimeException(
					"A repeater component cannot be used as the content of a modal window, please use repeater's parent");
		}
		component.setOutputMarkupPlaceholderTag(true);
		component.setVisible(true);
		replace(component);
		return this;
	}
}
