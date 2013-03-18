package org.lsqt.content.web.wicket;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.component.Clock;
import org.lsqt.content.web.wicket.component.menu.SimpleSDMenu;
import org.lsqt.content.web.wicket.component.menu.UrlNode;
import org.lsqt.content.web.wicket.component.tree.Node;

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
		final WebMarkupContainer header = new WebMarkupContainer("header");
		final WebMarkupContainer menu = new WebMarkupContainer("menu");
	
		final WebMarkupContainer sidebar = new WebMarkupContainer("sidebar");
	
		
		header.setOutputMarkupId(true);
		menu.setOutputMarkupId(true);
		
		sidebar.setOutputMarkupPlaceholderTag(true);
	
		SimpleSDMenu sideConetnt=new SimpleSDMenu("sideConetnt",null)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				UrlNode n=(UrlNode)node;
				
				try
				{
					System.out.println((WebPage)Class.forName(n.getUrl()).newInstance());
					setResponsePage((WebPage)Class.forName(n.getUrl()).newInstance());
				}catch (Exception e)
				{
					
					e.printStackTrace();
				}
			}
		};
		
		
		Clock clock = new Clock("time", TimeZone.getDefault());
		clock.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)));
		menu.add(clock);
		
		add(header);
		add(menu);
		add(sidebar);
		{
			sidebar.add(sideConetnt);
		}
		 
		
		
		
		
	}
	
}

