package com.lsqt.modules.resource.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;


/**
 * 新闻与新闻类别的多对多关系(两个一对多实现多对多)
 * @author 袁明敏
 *
 */
@Entity
@Table(name="lsqt_news")
public class News implements ResourceType ,Serializable{
	/****/
	private static final long serialVersionUID = 82924462940851815L;


	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	
	/**新闻关键字**/
	@Column(name="newsKey",length=500)
	private String newsKey;
	
	/**新闻标题**/
	@Column(name="title",length=200)
	private String title;
	
	
	/**内容**/
	@Column(name="content",length=2000)
	private String content;
	
	
	/**是否禁用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	/**是否发布**/
	@Column(name="isPublish")
	private Boolean isPublish;
	
	
	/**发布用户**/
	@Column(name="publishUser")
	private String publishUser;
	
	
	/**发布日期**/
	@Column(name="publishDate")
	private Date publishDate;
	
	
	/**更新日期**/
	@Column(name="lastUpdatDate")
	private Date lastUpdatDate;
	
	
	/**该新闻的评论计数**/
	@Column(name="commentCnt")
	private Integer commentCnt;
	
	/**动态网页地址**/
	@Column(name="dynamicHttpUrl",length=2000)
	private String dynamicHttpUrl;
	
	
	/**静态网页地址**/
	@Column(name="staticHttpUrl",length=2000)
	private String staticHttpUrl;
	
	
	/**是否启用动态地址访问**/
	@Column(name="isDynamic")
	private Boolean isDynamic;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNewsKey() {
		return newsKey;
	}
	public void setNewsKey(String newsKey) {
		this.newsKey = newsKey;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
	public String getPublishUser() {
		return publishUser;
	}
	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getLastUpdatDate() {
		return lastUpdatDate;
	}
	public void setLastUpdatDate(Date lastUpdatDate) {
		this.lastUpdatDate = lastUpdatDate;
	}
	public Integer getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}
	public String getDynamicHttpUrl() {
		return dynamicHttpUrl;
	}
	public void setDynamicHttpUrl(String dynamicHttpUrl) {
		this.dynamicHttpUrl = dynamicHttpUrl;
	}
	public String getStaticHttpUrl() {
		return staticHttpUrl;
	}
	public void setStaticHttpUrl(String staticHttpUrl) {
		this.staticHttpUrl = staticHttpUrl;
	}
	public Boolean getIsDynamic() {
		return isDynamic;
	}
	public void setIsDynamic(Boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

}
