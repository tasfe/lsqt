package org.lsqt.content.web.wicket.content.panel;

import java.util.ArrayList;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;
import org.lsqt.content.web.wicket.content.NewsAddPage;

public class ContentPanel extends Panel{
	/**  */
	private static final long serialVersionUID = 1L;
	@SpringBean NewsService newsServ;
	
	public ContentPanel(String id) {
		super(id);
		layout();
	}


	private void layout(){
		SimpleDataTable table=new SimpleDataTable("newsList");
		Page  initialPage=new Page(20,1);
		initialPage.addOrderProperties("createdDate", false);
		newsServ.loadPage(initialPage);
		table.bindData(new ArrayList( initialPage.getData()), 20).displayOn(new String[]{"name","title","sourceFrom"});
		
		
		Link<Void> link=new Link<Void>("btnAdd") {
			/** 	 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(NewsAddPage.class);
			}
		};
		
		add(table);
		add(link);
	}
}
