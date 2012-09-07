package org.lsqt.content.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;
import org.lsqt.content.model.mtm2.Student;

/**
 * 
 * @author 袁明敏
 *用于产品、新闻、论坛贴等信息分类
 */
@Entity
@Table(name="lsqt_category")
public class Category implements Serializable{
	/****/
	private static final long serialVersionUID = 3897093806881654371L;
	private static long OBJECT_COUNTER=0L;
	public Category(){
		OBJECT_COUNTER++;
	}

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="pid",insertable=false,updatable=false)
	private String pid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="createTime")
	private Long createTime=System.currentTimeMillis()+OBJECT_COUNTER;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pid", referencedColumnName = "id")
	private Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Category> subCategories=new HashSet<Category>();
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "categories", targetEntity = News.class)
	private Set<News> news;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
