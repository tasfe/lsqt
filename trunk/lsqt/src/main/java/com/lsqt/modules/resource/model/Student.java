package com.lsqt.modules.resource.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="test_student2")
public class Student {
	private String id;
	
	private String stuName;
	
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	
	@Column(name="stuName",length=50)
	public String getStuName() {
		return stuName;
	}

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
