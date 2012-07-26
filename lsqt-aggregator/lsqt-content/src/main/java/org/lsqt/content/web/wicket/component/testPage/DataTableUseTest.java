package org.lsqt.content.web.wicket.component.testPage;

import org.lsqt.content.web.wicket.AbstractPage;
import org.lsqt.content.web.wicket.component.datatable.SimpleDataTable;

public class DataTableUseTest extends AbstractPage {
	public DataTableUseTest(){
		add(new SimpleDataTable("table"));
	}
}
