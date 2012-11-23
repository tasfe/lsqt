package org.lsqt.content.web.wicket.component.tab;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

public abstract class MyAbstractTab extends AbstractTab {
	private String title;
	private boolean canBeClosed;

	public MyAbstractTab(IModel iModel, String title, boolean canBeClosed)
	{
		super(iModel);
		this.title = title;
		this.canBeClosed = canBeClosed;
	}

	public String getOngletTitle()
	{
		return this.title;
	}
	
	public boolean isCanBeClosed()
	{
		return canBeClosed;
	}

}
