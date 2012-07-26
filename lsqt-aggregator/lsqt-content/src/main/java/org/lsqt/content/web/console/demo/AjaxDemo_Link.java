package org.lsqt.content.web.console.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;
import org.lsqt.content.web.wicket.AbstractPage;

public class AjaxDemo_Link extends AbstractPage{
	private int count ;
	
	public AjaxDemo_Link(){
		
		final Label lbl=new Label("countLabel",new PropertyModel(this, "count"));
		lbl.setOutputMarkupId(true);
		
		AjaxLink ajaxLink=new AjaxLink("ajaxLink"){
			public void onClick(AjaxRequestTarget target) {
				count++;
				target.add(lbl);
				
				target.appendJavaScript("alert('hello')");
			}
		};
		
		add(lbl);
		add(ajaxLink);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
