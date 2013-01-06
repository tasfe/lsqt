package org.lsqt.content.web.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.content.dao.CategoryDao;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.User;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.component.tree.ModelUtil;
import org.lsqt.content.web.wicket.component.tree.SimpleTreeProvider;
import org.lsqt.content.web.wicket.content.bean.CategoryProvider;

public class ConsoleIndex extends AbstractPage {
	@SpringBean
	private CategoryService CatService;
	
	@SuppressWarnings("unchecked")
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
		

		

	/*	
		try {
			add(new DefaultNestedTree("simpleTree",new CategoryProvider())); 
		} catch (Exception e) {
			e.printStackTrace();
		}  */
	
	
	}


}

