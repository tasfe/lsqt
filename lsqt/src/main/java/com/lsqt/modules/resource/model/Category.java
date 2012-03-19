package com.lsqt.modules.resource.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity(dynamicInsert=true,dynamicUpdate=true) 
public class Category implements ResourceType , Serializable{
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="pid")
	private String pid;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="category")
	private Set<NewsCategory> NewsCategoryItems;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<NewsCategory> getNewsCategoryItems() {
		return NewsCategoryItems;
	}

	public void setNewsCategoryItems(Set<NewsCategory> newsCategoryItems) {
		NewsCategoryItems = newsCategoryItems;
	}
}
