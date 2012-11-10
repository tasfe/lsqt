package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.News;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;

public class NewsListPage  extends ConsoleIndex{

	@SpringBean NewsService newsServ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NewsListPage(){
		SimpleDataTable table=new SimpleDataTable("newsList");
		Page  initialPage=new Page(20,1);
		initialPage.addOrderProperties("createdDate", false);
		newsServ.loadPage(initialPage);
		table.bindList (  new ArrayList( initialPage.getData()), 20).displayOn(new String[]{"id","title","pubTime","sourceFrom"});
		add(table);
	}
}
