package org.lsqt.content.web.console.demo;

import org.apache.wicket.extensions.wizard.StaticContentStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class WizardPage extends AbstractPage {
	public WizardPage(){
	
		
		WizardModel model = new WizardModel( );
		Report report = new Report ();
		model.add( new StaticContentStep( "第一步", "查看报表须知", "我也不知道说什么",true));
		
		//加入功能须知向导步骤
		model.add( new ReportListWizardStep(report, "第二步", "选择报表"));
		//加入报表选择步骤
		model.add( new ReportParameterWizardStep( report, "第三步", "选择报表参数"));
		//加入报表参数选择步骤
		//model.add( new ReportContentWizardStep(report, "第四步", "查看报表结果"));
		//加入报表查看步骤
		Wizard wizard = new Wizard( "wizard", model);
		this.add(wizard);


	}
}
