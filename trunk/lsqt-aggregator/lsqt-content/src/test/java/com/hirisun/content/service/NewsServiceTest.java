package com.hirisun.content.service;

import org.junit.Test;
import org.lsqt.content.model.News;
import org.lsqt.content.service.NewsService;


import com.hirisun.AbstractTest;

public class NewsServiceTest extends AbstractTest
{
	final NewsService service=	(NewsService)getBean("newsServiceImpl");
	
	@Test
	public void save(){
		News n=new News();
		n.setName("name");
		n.setAuthor("author");
		service.save(n, "content");
	}
}
