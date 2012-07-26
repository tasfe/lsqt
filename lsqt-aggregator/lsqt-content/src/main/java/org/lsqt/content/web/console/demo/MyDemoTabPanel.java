package org.lsqt.content.web.console.demo;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;
import org.lsqt.content.web.wicket.component.tabpanel.TabPanel;

public class MyDemoTabPanel extends AbstractPage {
	public MyDemoTabPanel(){
		
		//customer
	TabPanel tab=new TabPanel("tab");
	add(tab);
		
		List<ITab> list=new LinkedList();
		
		
		//other tab implements
		list.add(new AbstractTab(new Model("第一个tab")) {
			public WebMarkupContainer getPanel(String panelId) {
				return new TabPanel(panelId);
			}
		});
		
		list.add(new AbstractTab(new Model("第二个tab")) {
			public WebMarkupContainer getPanel(String panelId) {
				return new TabPanel(panelId);
			}
		});
		
		add(new TabbedPanel( "tabs", list));
	}
}
