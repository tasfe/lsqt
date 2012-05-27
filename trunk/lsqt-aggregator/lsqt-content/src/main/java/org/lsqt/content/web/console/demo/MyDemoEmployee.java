package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDemoEmployee {
	private String account;
	private String name ;
	private String password;
	private boolean female ;
	private Date birthday;
	private double salary;
	private String job;
	private int workYear;
	private boolean married ;
	private List favorites = new ArrayList();
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFemale() {
		return female;
	}
	public void setFemale(boolean female) {
		this.female = female;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getWorkYear() {
		return workYear;
	}
	public void setWorkYear(int workYear) {
		this.workYear = workYear;
	}
	public boolean isMarried() {
		return married;
	}
	public void setMarried(boolean married) {
		this.married = married;
	}
	public List getFavorites() {
		return favorites;
	}
	public void setFavorites(List favorites) {
		this.favorites = favorites;
	}

}
