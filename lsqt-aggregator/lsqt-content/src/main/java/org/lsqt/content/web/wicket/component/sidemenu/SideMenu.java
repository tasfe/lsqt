package org.lsqt.content.web.wicket.component.sidemenu;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class SideMenu extends Panel
{
	public SideMenu(String id)
	{
		super(id);
	}

	public SideMenu(String id,IModel<?> model){
		super(id,model);
	}
	
}
