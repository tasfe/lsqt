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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="lsqt_category")
public class Category implements Serializable{
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="pid",insertable=false,updatable=false)
	private String pid;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "pid", referencedColumnName = "id")
	private Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Category> subCategories=new HashSet<Category>();
	
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
}
