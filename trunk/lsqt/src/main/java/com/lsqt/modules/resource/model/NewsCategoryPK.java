package com.lsqt.modules.resource.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
@SuppressWarnings("serial")
public class NewsCategoryPK implements Serializable{
	private News news;
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="news_id",referencedColumnName="id")
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="id")
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
