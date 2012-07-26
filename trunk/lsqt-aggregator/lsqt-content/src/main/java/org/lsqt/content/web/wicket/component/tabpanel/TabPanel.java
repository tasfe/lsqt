package org.lsqt.content.web.wicket.component.tabpanel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class TabPanel extends Panel {
	public TabPanel(String id){
		super(id);
		Label lbl=new Label("label","");
		add(lbl);
	}
}
