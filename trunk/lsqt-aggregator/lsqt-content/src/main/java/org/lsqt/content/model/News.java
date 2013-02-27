package org.lsqt.content.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_news")
public class News extends Content implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**新闻标题**/
	@Column(name="title",length=500)
	private String title;
	
	/**标签**/
	@Column(name="tag",length=200)
	private String tag;
	
	/**摘要**/
	@Column(name="shortContent",length=500)
	private String shortContent;
	
	/**作者**/
	@Column(name="author",length=50)
	private String author;
	
	/**新闻前台在线日期**/
	@Column(name="onlineTime")
	private String onlineTime;
	
	/**(状态)是否启用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	/**是否已发布**/
	@Column(name="isPublished")
	private Boolean isPublished;
	
	/**后台发布日期**/
	@Column(name="pubTime")
	private String pubTime;
	
	/**是否已生成静态页**/
	private Boolean isStatic;
	
	
	/**当前新闻所属的应用(ID)**/
	@Column(name="app_id",insertable=false,updatable=false)
	private String appId;
	
	/**新闻所属的类别，一个新闻可以同时属于两个或多个类别**/
	@ManyToMany(targetEntity = Category.class, cascade = { CascadeType.MERGE,CascadeType.PERSIST })
	@JoinTable(name = "tb_news_category", joinColumns = { @JoinColumn(name = "news_id") }, inverseJoinColumns = { @JoinColumn(name = "category_id") })
	private Set<Category> categories=new HashSet<Category>();
	
	
	/**当前新闻所属的应用（应用与新闻的多对一关系）**/
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	private Application app;
	
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubTime() {
		return pubTime;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public Boolean getIsStatic() {
		return isStatic;
	}
	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}
	public String getShortContent() {
		return shortContent;
	}
	public void setShortContent(String shortContent) {
		this.shortContent = shortContent;
	}
	public Application getApp() {
		return app;
	}
	public void setApp(Application app) {
		this.app = app;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getContentKeys()
	{
		return contentKeys;
	}
	public void setContentKeys(String contentKeys)
	{
		this.contentKeys = contentKeys;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getOnlineTime()
	{
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime)
	{
		this.onlineTime = onlineTime;
	}
}
