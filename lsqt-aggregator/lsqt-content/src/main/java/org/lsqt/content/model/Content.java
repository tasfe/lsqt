package org.lsqt.content.model;

import javax.persistence.MappedSuperclass;

/**
 *所有内容抽象
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 * @param <T>
 * 
 */
@MappedSuperclass
public abstract class Content {
	/**内容标识**/
	protected String id;
	/**内容名称**/
	protected String name;
	/**内空关键字(以逗号分隔)**/
	protected String keys;
	/**描述信息**/
	protected String description;
	/**创建时间戳**/
	protected Long createTime;
	
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
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
}
