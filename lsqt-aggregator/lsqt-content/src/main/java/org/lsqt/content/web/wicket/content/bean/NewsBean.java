package org.lsqt.content.web.wicket.content.bean;

import org.apache.commons.lang.StringUtils;
import org.lsqt.content.model.News;

/**
 * 用于Wicket Web页面的数据封装.
 * @author 袁明敏
 *
 */
public class NewsBean implements java.io.Serializable
{
	private News news=new News();
	private String content=StringUtils.EMPTY;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}
}
