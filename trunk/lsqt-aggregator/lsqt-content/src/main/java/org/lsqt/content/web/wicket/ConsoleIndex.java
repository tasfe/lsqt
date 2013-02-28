package org.lsqt.content.web.wicket;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.component.Clock;

/**
 *后台管理首页.
 * @author 袁明敏
 *
 */
public class ConsoleIndex extends AbstractPage {
	/**  */
	private static final long serialVersionUID = 1L;
	
	@SpringBean private CategoryService CatService;
	
	public ConsoleIndex() {
		List<Category> list=new ArrayList<Category>();
		Category c=new Category();
		c.setId("2");
		c.setPid("2");
		c.setDescription("org.lsqt.content.web.wicket.ConsoleIndex");
		c.setName("系统管理[c]");
		
		Category c2=new Category();
		c2.setId("1");
		c2.setPid("2");
		c2.setName("内容管理[c2]");
		c2.setDescription("org.lsqt.content.web.wicket.content.NewsMainPage");
		
		
		
		Category c3=new Category();
		c3.setId("3");
		c3.setPid("1");
		c3.setName("系统类别[c3]");
		c3.setDescription("org.lsqt.content.web.wicket.content.CategoryPage");
		
		
		
		Category c4=new Category();
		c4.setId("9");
		c4.setPid("1");
		c4.setName("c[c4]");
		
		
		
		Category c5=new Category();
		c5.setId("13");
		c5.setPid("3");
		c5.setName("d[c5]");
		c5.setParentCategory(c3);
		
		c.getSubCategories().add(c2);
		
		c2.setParentCategory(c);
		c2.getSubCategories().add(c4);
		c2.getSubCategories().add(c3);
		
		c3.setParentCategory(c2);
		c3.getSubCategories().add(c5);
		
		c4.setParentCategory(c2);

		
		list.add(c);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
	
		layout();
	}

	private void layout(){
		//WebMarkupContainer container = new WebMarkupContainer("container");
		final WebMarkupContainer header = new WebMarkupContainer("header");
		final WebMarkupContainer menu = new WebMarkupContainer("menu");
		//WebMarkupContainer mainContent = new WebMarkupContainer("mainContent");
		final WebMarkupContainer sidebar = new WebMarkupContainer("sidebar");
		//WebMarkupContainer content = new WebMarkupContainer("content");
		
		//container.setOutputMarkupId(true);
		header.setOutputMarkupId(true);
		menu.setOutputMarkupId(true);
		//mainContent.setOutputMarkupId(true);
		sidebar.setOutputMarkupPlaceholderTag(true);
		//content.setOutputMarkupId(true);
		
		AjaxLink<Void> btnTop=new AjaxLink<Void>("btnTop") {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			
				
			}
		};
		
		AjaxLink<Void> btnLeft=new AjaxLink<Void>("btnLeft") {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				StringBuffer js=new StringBuffer();
				js.append("$(function(){" +
						"            $('#content').css('width','950px');" +
						"   	})");
				
				
				target.appendJavaScript(js.toString());
				if (sidebar.isVisible()) {
					sidebar.setVisible(false);
				}
				target.add(sidebar);
				
			}
		};
		
		AjaxLink<Void> btnRight=new AjaxLink<Void>("btnRight") {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				StringBuffer js=new StringBuffer();
				js.append("$(function(){" +
						"            $('#content').css('width','750px');" +
						"   	})");
				
				
				target.appendJavaScript(js.toString());
				if (sidebar.isVisible() == false) {
					sidebar.setVisible(true);
				}
				target.add(sidebar);
				
			}
		};
		
		
		add(header);
		add(menu);
		add(sidebar);
		 
		menu.add(btnTop);
		menu.add(btnLeft);
		menu.add(btnRight);
		
		
		
		//Clock clock = new Clock("clock", TimeZone.getTimeZone("China/beijing"));
		

		// add the ajax behavior which will keep updating the component every 5
		// seconds
		//clock.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
		//menu.add(clock);
	}
	
}

