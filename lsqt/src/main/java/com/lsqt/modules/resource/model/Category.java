package com.lsqt.modules.resource.model;

import java.io.Serializable;

import org.hibernate.annotations.Entity;

@SuppressWarnings("serial")
@Entity(dynamicInsert=true,dynamicUpdate=true) 
public class Category implements ResourceType , Serializable{
	private String id;
	private String name;
	private String pid;

}
