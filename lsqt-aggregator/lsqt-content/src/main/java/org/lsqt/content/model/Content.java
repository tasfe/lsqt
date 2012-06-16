package org.lsqt.content.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 *所有内容抽象，如：新闻、贴子、招聘、文学等信息内容抽象
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 * 
 */
@MappedSuperclass
public abstract class Content {
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	protected String id;
	
	/**内容名称**/
	@Column(name="name",length=500)
	protected String name;
	
	/**内容**/
	@Column(name="content",length=2000)
	protected String content;
	
	/**内空关键字(以逗号分隔)**/
	@Column(name="contentKeys",length=200)
	protected String contentKeys;
	
	/**描述信息**/
	@Column(name="description",length=1000)
	protected String description;
	
	/**创建时间戳**/
	@Column(name="createTime")
	protected Long createTime=System.currentTimeMillis();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentKeys() {
		return contentKeys;
	}
	public void setContentKeys(String contentKeys) {
		this.contentKeys = contentKeys;
	}
}
