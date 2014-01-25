package org.lsqt.components.dto;

public class Car {
	private double carNum;
	public Car(){
		
	}
	public Car(double carnum){
		this.carNum=carnum;
	}
	public double getCarNum() {
		return carNum;
	}

	public void setCarNum(double carNum) {
		this.carNum = carNum;
	}
}
