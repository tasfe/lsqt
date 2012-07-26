package org.lsqt.content.web.console.demo;

import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IErrorMessageSource;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.IValidator;

public class ReportListWizardStep extends WizardStep {
	private static List<String> REPORT_LIST=new LinkedList<String>();
	static{
		for(int i=1;i<=10;i++){
			REPORT_LIST.add("报表"+i);
		}
	}
	
	public ReportListWizardStep(Report report , String title, String content){
		super(title,content);
		final RadioGroup radioGroup=new RadioGroup("group",new PropertyModel(REPORT_LIST, "name"));
		add(radioGroup);
		
		radioGroup.add(new IValidator(){ //通过添加一个自定义的验证器,保证用户一定要选择一个报表
			public void validate(IValidatable validatable) {
				PropertyModel model=(PropertyModel)radioGroup.getModel();
				if(null == model){
					validatable.error(new IValidationError() {
						public String getErrorMessage(IErrorMessageSource messageSource) {
							return "必须选择报表名称";
						}
					});
				}
			}
		});
		
		radioGroup.add(new ListView("reports",REPORT_LIST){
			protected void populateItem(ListItem item) {
				item.add( new Radio( "selected", item.getModel()));
				item.add( new Label( "name", item.getModel()));
			}
		}) ;
		
	}
} 
