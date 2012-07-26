package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.hsqldb.lib.StringUtil;
import org.lsqt.content.model.User;
import org.lsqt.content.web.wicket.AbstractPage;

public class AjaxDemo_DropDownChoice extends AbstractPage {
	private List secondList = new ArrayList();
	
/*	private String firstName;
	private String secondName;*/

	public AjaxDemo_DropDownChoice() {
		super();

		List firstList = new ArrayList();
		firstList.add("1");
		firstList.add("2");
		firstList.add("3");
		
		User user=new User();
		Form form = new Form("form", new CompoundPropertyModel(user));
		final DropDownChoice choice1 = new DropDownChoice("firstName",firstList);
		final DropDownChoice choice2 = new DropDownChoice("secondName",secondList);
		form.setOutputMarkupId(true);
		choice1.setOutputMarkupId(true);

		choice2.setOutputMarkupId(true);
		form.add(choice1);
		form.add(choice2);

		this.add(form);
		choice1.add(new AjaxFormComponentUpdatingBehavior("onChange"){
			protected void onUpdate(AjaxRequestTarget target) {
				secondList.clear();
				int index = NumberUtils.toInt((String) choice1.getModelObject());

				for (int i = 1; i < 5; i++) {
					secondList.add(Integer.toString(index * 10 + i));
				}
				target.add(choice2);
			}
		});
	}

/*	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}*/
}
