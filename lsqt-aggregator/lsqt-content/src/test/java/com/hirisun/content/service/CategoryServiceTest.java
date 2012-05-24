package com.hirisun.content.service;

import org.hibernate.cache.EhCacheProvider;
import org.junit.Assert;
import org.junit.Test;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.CategoryServiceImpl;

import com.hirisun.AbstractTest;

public class CategoryServiceTest extends AbstractTest{
	final CategoryServiceImpl service=	getBean(CategoryServiceImpl.class);
	
	@Test
	public void testGetRoot(){
		Category categoryRoot=service.getRoot();
		Assert.assertNotNull(categoryRoot);
	}
	
	@Test
	public void testCRUDCategory(){
		
		
		Category categoryRoot=service.getRoot();
		if(categoryRoot!=null){
			service.deleteById(categoryRoot.getId());
		}
		
		/*＊
		 * 添加类别,如下：
		 * ----新闻
		 * -------|-------财经新闻
		 * -------|-------娱乐新闻
		 * -------|-------中央财经
		**/
		Category c=new Category();
		c.setName("新闻");
		c.setParentCategory(c);
		
		
		Category c2=new Category();
		c2.setName("财经新闻");
		c2.setParentCategory(c);
		service.save(c2);
		
		Category c3=new Category();
		c3.setName("娱乐新闻");
		c3.setParentCategory(c);
		service.save(c3);
		
		Category c4=new Category();
		c4.setName("中央财经");
		c4.setParentCategory(c);
		service.save(c4);
		
		
		/*
		 * 中央财经移到财经类别下,如下：
		 * ----新闻
		 * -------|-------财经新闻
		 *                       |-------------中央财经
		 *                       |-------------地方财经
		 * -------|-------娱乐新闻
		 * 
		 * */
		c4.setParentCategory(c2);
		c4=service.update(c4);
		
		Category c5=new Category();
		c5.setName("地方财经");
		c5.setParentCategory(c2);
		service.save(c5);
	}
}
