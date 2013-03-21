package org.lsqt.content.web.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.lsqt.content.web.wicket.component.container.TopFixedFloatPanel;

public class TestPage extends WebPage
{
	public TestPage()
	{
		add(new TopFixedFloatPanel("top"));
	}
}
