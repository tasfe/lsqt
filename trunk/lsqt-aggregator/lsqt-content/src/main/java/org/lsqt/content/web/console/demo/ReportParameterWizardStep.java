package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class ReportParameterWizardStep extends WizardStep {
	private static List parameters = new ArrayList();
	static {
		for (int i = 0; i < 5; i++) {
			parameters.add("parameter- " + i);
		}
	}

	public ReportParameterWizardStep(Report report, String title, String content){
		super(title, content);
		CheckGroup group = new CheckGroup("group", report.getParameters());
		this.add(group);
		group.add( new CheckGroupSelector( "groupSelector"));

		group.add( new ListView( "parameters", parameters ){
			protected void populateItem(ListItem item) {
				item.add( new Check( "checkbox",item.getModel()));
				item.add( new Label( "name",(String) item.getModelObject()));
			}
		});
	}
}
