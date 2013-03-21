package org.lsqt.content.web.wicket.component.container;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.AbstractRepeater;

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
