package com.hirisun.content.service;

import org.hibernate.cache.EhCacheProvider;
import org.junit.Assert;
import org.junit.Test;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.CategoryServiceImpl;

import com.hirisun.AbstractTest;

public class CategoryServiceTest extends AbstractTest{
	@Test
	public void testCRUDCategory(){
		
		Category c=new Category();
		c.setName("新闻");
		CategoryServiceImpl service=	getBean(CategoryServiceImpl.class);
		Assert.assertNotNull(service);
		service.save(c);
	}
}
