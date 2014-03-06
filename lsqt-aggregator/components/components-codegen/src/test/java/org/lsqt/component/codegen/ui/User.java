package org.lsqt.component.codegen.ui;

public class User {
	public static final String TMPL_FOLDER="/Users/yuanke/Documents/qhee/workspace/lsqt-aggregator/components/components-codegen/src/test/java/org/lsqt/component/codegen/ui";
	
	private String name;
	private String sex;
	private Long id;
	private java.util.Date birthday;
	private String address;
	
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
	
}
