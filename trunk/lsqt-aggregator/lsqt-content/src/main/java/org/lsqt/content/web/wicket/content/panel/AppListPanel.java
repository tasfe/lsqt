package org.lsqt.content.web.wicket.content.panel;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;

public class AppListPanel extends SimpleDataView {
	@SpringBean AppsService appsService;
	
	public AppListPanel(String id) {
		super(id);
	}

	@Override
	public void loadPage(Page page) {
		appsService.loadPage(page);
	}
 
	
}
