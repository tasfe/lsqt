package com.hirisun.content.web.temptest.aop;

import java.util.Random;

public class MyxxxServiceImpl implements MyxxxService {
	public void eating(){
		try {
			Random r=new Random();
		
			//System.out.println("随机值："+Math.abs(r.nextInt()/5999999));
			Thread.currentThread().sleep(Math.abs(r.nextInt()/5999999));
			System.out.println("eating....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
