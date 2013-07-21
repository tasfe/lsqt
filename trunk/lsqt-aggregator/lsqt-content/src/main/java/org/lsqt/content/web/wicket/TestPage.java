package org.lsqt.content.web.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.lsqt.content.web.wicket.component.container.TopFixedFloatPanel;
import org.lsqt.content.web.wicket.component.menu.TopMenu.SecondaryMenu;

public class TestPage extends WebPage
{
	public TestPage()
	{
		
		add(new SecondaryMenu("topMenu"));
	}
}
