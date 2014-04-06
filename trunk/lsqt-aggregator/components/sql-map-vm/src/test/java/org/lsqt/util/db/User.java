package org.lsqt.util.db;


public class User {
	private Long id;
	
	private String name;
	
	private String sex;
	
	private java.util.Date birthday;
	
	private String address;
	
	private boolean isBad;
	
	private boolean hasTool;
	
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
