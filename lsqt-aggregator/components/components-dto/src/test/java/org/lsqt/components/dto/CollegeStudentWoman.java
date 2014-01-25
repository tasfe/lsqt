package org.lsqt.components.dto;

public class CollegeStudentWoman extends CollegeStudent {
	private boolean isSex;
	private boolean hasSex;
	private boolean haveSex;
	private boolean hadSex;
	private boolean havingSex;

	private Car car;

	public boolean isSex() {
		return isSex;
	}

	public boolean isHasSex() {
		return hasSex;
	}

	public boolean isHaveSex() {
		return haveSex;
	}

	public boolean isHadSex() {
		return hadSex;
	}

	public boolean isHavingSex() {
		return havingSex;
	}

	public Car getCar() {
		return car;
	}

	public void setSex(boolean isSex) {
		this.isSex = isSex;
	}

	public void setHasSex(boolean hasSex) {
		this.hasSex = hasSex;
	}

	public void setHaveSex(boolean haveSex) {
		this.haveSex = haveSex;
	}

	public void setHadSex(boolean hadSex) {
		this.hadSex = hadSex;
	}

	public void setHavingSex(boolean havingSex) {
		this.havingSex = havingSex;
	}

	public void setCar(Car car) {
		this.car = car;
	}

}
