package org.lsqt.content.web.wicket.content.panel;

import java.util.ArrayList;

import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;
import org.lsqt.content.web.wicket.content.CategoryAddPage;

public class CategoryListPanel extends Panel {

	/**  */
	private static final long serialVersionUID = 1L;

	@SpringBean CategoryService  categoryServ;
	
	public CategoryListPanel(String id) {
		super(id);
		layout();
	}

	private void layout(){
		Link<Void> btnAdd=new Link<Void>("btnAdd"){
			/**  */
			private static final long serialVersionUID = 1L;

			public void onClick() {
				setResponsePage(CategoryAddPage.class);
			};
		} ;
		add(btnAdd);
		
		
		SimpleDataTable table=new SimpleDataTable("table");
		Page initPage=new Page(20,1);
		initPage.addOrderProperties("createTime", false);
		initPage=categoryServ.loadPage(initPage);
		table.bindData(new ArrayList(initPage.getData()), 20).displayOn(
				new String[] { "name", "description", "orderNum",
						"hasChildNode", "levelNum" });
		add(table);
	}
}