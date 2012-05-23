package org.lsqt.content.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="lsqt_news")
public class News extends Content implements Serializable{
	/**新闻标题**/
	@Column(name="title",length=500)
	private String title;
	
	/**新闻日期**/
	@Column(name="time")
	private Long time;
	
	/**是否启用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	/**是否已发布**/
	@Column(name="isPublished")
	private Boolean isPublished;
	
	/**后台发布日期**/
	@Column(name="pubTime")
	private Long pubTime;
	
	/**后台发布人**/
	@Column(name="pubUserId")
	private String pubUserId;
	
	/**新闻来源内容（默认后台上报，其次有RSS来源、网页抓取分析等）**/
	@Column(name="sourceFrom",length=1000)
	private String sourceFrom;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getPubTime() {
		return pubTime;
	}
	public void setPubTime(Long pubTime) {
		this.pubTime = pubTime;
	}
	public String getPubUserId() {
		return pubUserId;
	}
	public void setPubUserId(String pubUserId) {
		this.pubUserId = pubUserId;
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
	
}