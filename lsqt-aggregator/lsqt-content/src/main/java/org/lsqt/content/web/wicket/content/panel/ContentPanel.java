package org.lsqt.content.web.wicket.content.panel;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.lsqt.content.model.Application;

public abstract class ContentPanel extends Panel{
	/**  */
	private static final long serialVersionUID = 1L;
	
	
	public ContentPanel(String id) {
		super(id);
	}

	public ContentPanel(final String id, final IModel<?> model) {
		super(id, model);
	}
	
	
}
