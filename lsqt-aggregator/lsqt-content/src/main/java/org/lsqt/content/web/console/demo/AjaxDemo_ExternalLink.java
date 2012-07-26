package org.lsqt.content.web.console.demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormValidatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.validation.validator.StringValidator;
import org.lsqt.content.web.wicket.AbstractPage;

public class AjaxDemo_ExternalLink extends AbstractPage {
	private String name;
	
	public AjaxDemo_ExternalLink(){
		final FeedbackPanel feedbackPanel=new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);
		
		Form form=new Form("form",new CompoundPropertyModel(this));
		add(form);
		
		final RequiredTextField rtf= new RequiredTextField("name");
		rtf.add(StringValidator.minimumLength(4));
		rtf.setOutputMarkupId(true);
		form.add(rtf);
		
/*		AjaxFormValidatingBehavior behavior=new  AjaxFormValidatingBehavior(form,"onclick"){
			protected void onError(org.apache.wicket.ajax.AjaxRequestTarget target) {
				target.add(feedbackPanel);
				super.onError(target);
			};
		};
		
		behavior.setThrottleDelay(Duration.ONE_SECOND); //每秒向服务器请求一次数据验证
		rtf.add(behavior);*/
		
		form.add(new AjaxSubmitLink("ajaxLink",form){
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				//sfeedbackPanel.setVisible(false);
				System.out.println("test_test"+name);
				form.remove(feedbackPanel);
			}

			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
				System.err.println("ajaxLink.... submit");
			}
		});
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
