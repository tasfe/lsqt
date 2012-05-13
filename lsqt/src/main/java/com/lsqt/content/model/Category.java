package com.lsqt.content.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.GenericGenerator;

import com.lsqt.modules.resource.model.ResourceType;

@SuppressWarnings("serial")
@Entity(dynamicInsert=true,dynamicUpdate=true) 
@Table(name="lsqt_category")
public class Category implements ResourceType , Serializable{
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="pid")
	private String pid;
	
	@Column(name="name")
	private String name;
	
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
}
