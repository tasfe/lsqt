package org.lsqt.content.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.lsqt.content.web.wicket.util.VarUtil;

/**
 *所有内容抽象，如：新闻、贴子、招聘、文学等信息内容抽象
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 * 
 */
@MappedSuperclass
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public abstract class Content {
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	protected String id;
	
	/** 编号 **/
	@Column(name="code",length=10)
	protected String code;
	
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
	protected String createTime=new DateTime().toString(VarUtil.DEFAULT_DATA_PATTERN);
	
	/**记录修改日期**/
	@Column(name="modifyDate")
	private String modifyDate=new DateTime().toString(VarUtil.DEFAULT_DATA_PATTERN);
	
	/**排序号**/
	@Column(name="orderNum")
	protected Integer orderNum;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
