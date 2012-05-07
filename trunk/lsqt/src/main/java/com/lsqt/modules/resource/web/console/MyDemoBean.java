package com.lsqt.modules.resource.web.console;

import java.io.Serializable;


public class MyDemoBean implements Serializable{

	/****/
	private static final long serialVersionUID = 5875507355874520780L;
	
	private String birthday;
	private String birthday2;
	private String message;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthday2() {
		return birthday2;
	}

	public void setBirthday2(String birthday2) {
		this.birthday2 = birthday2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
