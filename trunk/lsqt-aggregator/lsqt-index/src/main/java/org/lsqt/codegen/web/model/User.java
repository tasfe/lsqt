package org.lsqt.codegen.web.model;

import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;

@Table(name="test_user",schema="oaonsite")
public class User {
	@Id
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="birthday")
	private java.util.Date birthday;
	
	@Column(name="address")
	private String address;
	
	@Column(name="isBad")
	private boolean isBad;
	
	@Column(name="hasTool")
	private boolean hasTool;
	
	@Column(name="msTool")
	private boolean msTool;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isBad() {
		return isBad;
	}

	public void setBad(boolean isBad) {
		this.isBad = isBad;
	}

	public boolean isHasTool() {
		return hasTool;
	}

	public void setHasTool(boolean hasTool) {
		this.hasTool = hasTool;
	}

	public boolean isMsTool() {
		return msTool;
	}

	public void setMsTool(boolean msTool) {
		this.msTool = msTool;
	}

}
