package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.tab.MyAbstractTab;
import org.lsqt.content.web.wicket.component.tab.MyTabbedPanel;
import org.lsqt.content.web.wicket.component.tab.SimpleTab;
import org.lsqt.content.web.wicket.content.panel.CategoryListPanel;
import org.lsqt.content.web.wicket.content.panel.ContentPanel;
import org.lsqt.content.web.wicket.content.panel.TmplPanel;

/**
 * 
 *新闻管理选项卡
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 */
public class NewsMainPage extends ConsoleIndex {
	
	/**  */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public NewsMainPage(){
		SimpleTab tab=new SimpleTab("tabs");
		tab.addPanel(CategoryListPanel.class, "栏目");
		tab.addPanel(TmplPanel.class, "模板");
		tab.addPanel(ContentPanel.class,"内容");
		add(tab);
		
	}
}
