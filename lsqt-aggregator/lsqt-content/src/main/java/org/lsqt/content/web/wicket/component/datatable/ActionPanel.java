package org.lsqt.content.web.wicket.component.datatable;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.lsqt.content.model.User;
import org.lsqt.content.web.console.demo.MyDemoPage10;

public class ActionPanel extends Panel {
	public ActionPanel(String id,Object entity){
		super(id);
		CheckBox selectedCheckBox=new CheckBox("selected",new PropertyModel(entity,"id"));
		add(selectedCheckBox);
		
		Link editLink=new Link("edit"){
			public void onClick() {
				
			}
		};
		add(editLink);
		
		Link deleteLink=new Link("delete"){
			public void onClick() {
				
			}
		};
		add(deleteLink);
		
		
		Link detailLink=new Link("detail"){
			public void onClick() {
				PageParameters param=new PageParameters();
				param.add("userName", "test", 0);
				
				setResponsePage(MyDemoPage10.class,param);
			}
		};
		add(detailLink);
	}
}
