package org.lsqt.content.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 *应用名称 用于新闻系统或论坛，电子商务网站的站点应用定义。
 *一个应用下有多个栏目，应用与栏目隶属于一对多的关系，多个栏目只可复制（复制多个栏目数据到一个应用下）到另一个应用，但不能多对多的关联
 * @author 袁明敏
 * @version 1.1
 * @since 2012-11-13
 * 
 */
@Entity
@Table(name="tb_application")
public class Application  implements Serializable{
	private static long OBJECT_COUNTER=0L;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	protected String id;
	
	/**应用名称**/
	@Column(name="name",length=500)
	protected String name;

	@Column(name="createTime")
	private Long createTime=System.currentTimeMillis()+(++OBJECT_COUNTER);
	
	@Column(name="description")
	private String description;
	
	/**类别排序号**/
	@Column(name="orderNum")
	private Integer orderNum;
	
	/**一个应用下的栏目**/
	@OneToMany(mappedBy="app",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Category> categories=new HashSet<Category>();
	
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
