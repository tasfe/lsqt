package com.lsqt.modules.resource.model;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;

/**
 * 
 * 
 * @author 袁明敏
 *
 */
@Entity(dynamicInsert=true,dynamicUpdate=true) 
@Table(name="LSQT_NEWS_CATEGORY")
@IdClass(NewsCategoryPK.class)
public class NewsCategory {
	private News news;
	private Category category;
	@Id
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	@Id
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
}
