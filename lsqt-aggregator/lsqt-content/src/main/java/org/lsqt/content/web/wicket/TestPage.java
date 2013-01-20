package org.lsqt.content.web.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.hibernate.dialect.MySQL5Dialect;
public class TestPage extends WebPage{
	@SpringBean AppsService appsService;
	
	public TestPage () {
		final String [] headLabel=new String[]{"id","name"};
		SimpleDataView t=new SimpleDataView("ctnView"){
			@Override
			protected void loadPage(org.lsqt.components.dao.suport.Page page) {
				appsService.loadPage(page);
			}
			
		};
		add(t);
		t.addHeadLabel(new String[]{"id","name"});
		t.addHeadProp(new String[]{"id","name"});
	}
}
