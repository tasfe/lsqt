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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.lsqt.content.model.mtm2.Course;

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
	
	/**新闻摘要**/
	@Column(name="shortContent",length=2000)
	private String shortContent;
	
	/**新闻前台在线日期**/
	@Column(name="onlineTime")
	private Date onlineTime;
	
	/**(状态)是否启用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	/**是否已发布**/
	@Column(name="isPublished")
	private Boolean isPublished;
	
	/**后台发布日期**/
	@Column(name="pubTime")
	private Long pubTime;
	
	/**是否生成静态页**/
	private Boolean isStatic;
	
	/**记录产生日期**/
	@Column(name="createdDate")
	private Date createdDate;
	
	/**记录修改日期**/
	@Column(name="modifyDate")
	private Date modifyDate;
	
	/**新闻来源内容（默认后台上报，其次有RSS来源、网页抓取分析等）**/
	@Column(name="sourceFrom",length=1000)
	private String sourceFrom;
	
	/**当前新闻所属的应用(ID)**/
	@Column(name="app_id",insertable=false,updatable=false)
	private String appId;
	
	/**新闻所属的类别，一个新闻可以同时属于两个或多个类别**/
	@ManyToMany(targetEntity = Category.class, cascade = { CascadeType.MERGE,CascadeType.PERSIST })
	@JoinTable(name = "tb_news_category", joinColumns = { @JoinColumn(name = "news_id") }, inverseJoinColumns = { @JoinColumn(name = "category_id") })
	private Set<Category> categories;
	
	
	/**当前新闻所属的应用（应用与新闻的多对一关系）**/
	@ManyToOne(cascade = CascadeType.ALL)
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
	public Long getPubTime() {
		return pubTime;
	}
	public void setPubTime(Long pubTime) {
		this.pubTime = pubTime;
	}
	public String getSourceFrom() {
		return sourceFrom;
	}
	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
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
	public Date getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	@Override
	public String toString() {
		return "News [title=" + title + ", onlineTime=" + onlineTime
				+ ", isEnable=" + isEnable + ", isPublished=" + isPublished
				+ ", pubTime=" + pubTime + ", createdDate=" + createdDate
				+ ", modifyDate=" + modifyDate + ", sourceFrom=" + sourceFrom
				+ ", categories=" + categories + ", id=" + id + ", name="
				+ name + ", content=" + content + ", contentKeys="
				+ contentKeys + ", description=" + description
				+ ", createTime=" + createTime + "]";
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
}
