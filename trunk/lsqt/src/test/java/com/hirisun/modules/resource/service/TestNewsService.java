package com.hirisun.modules.resource.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.hirisun.AbstractTest;
import com.lsqt.modules.resource.model.News;
import com.lsqt.modules.resource.service.NewsService;

public class TestNewsService extends AbstractTest {
	@Test
	public void testCRUDNews(){
		News news=new News();
		news.setCommentCnt(100);
		news.setContent("这是新闻内容");
		news.setDynamicHttpUrl("http://lsqt.org/news?id=1");
		news.setIsDynamic(false);
		news.setIsEnable(false);
		news.setIsPublish(false);
		news.setLastUpdatDate(new Date());
		news.setNewsKey("牛Ｂ,国庆");
		news.setPublishDate(new Date());
		news.setStaticHttpUrl("http://lsqt.org/news/html/1.html");
		news.setTitle("新闻标题");
		
		NewsService newsService=getBean(NewsService.class);
		newsService.saveNews(news);
		Assert.assertNotNull(news.getId());
		
		news.setCommentCnt(8888);
		news.setContent("这是新闻内容2");
		news.setDynamicHttpUrl("http://lsqt.org/news?id=2");
		news.setIsDynamic(true);
		news.setIsEnable(true);
		news.setIsPublish(true);
		news.setLastUpdatDate(new Date());
		news.setNewsKey("牛A,中秋");
		news.setPublishDate(new Date());
		news.setStaticHttpUrl("http://lsqt.org/news/html/2.html");
		news.setTitle("新闻标题2");
		newsService.updateNews(news);
		
		newsService.deleteById(news.getId());
		
		Assert.assertNull(newsService.findNewsById(news.getId()));
		
	}
}
