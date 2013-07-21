package org.lsqt.content.web.wicket;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

public class TopFloatContentPanel extends Panel
{

	public TopFloatContentPanel(String id)
	{
		super(id);
		
		AjaxLink<Void> link=new AjaxLink<Void>("testPage")
		{
			
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				setResponsePage(TestPage.class);
			}
		};
		add(link);
	}

}
