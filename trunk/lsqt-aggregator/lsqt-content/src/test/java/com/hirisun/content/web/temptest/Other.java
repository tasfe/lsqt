package com.hirisun.content.web.temptest;

import java.util.WeakHashMap;

import org.apache.wicket.model.Model;
import org.junit.Test;

public   class Other {
	@Test
	public  void test1(){
		new Model("");
		
		float aFloat = 0.6710339f;
		double aDouble = 0.04150553411984792d;
		double sum = aFloat + aDouble;
		float quotient = (float)(aFloat / aDouble);
		System.out.println("float: " + aFloat);
		System.out.println("double: " + aDouble);
		System.out.println("sum: " + sum);
		System.out.println("quotient: " + quotient);
		
		System.out.println(Math.round(11.5));
		System.out.println(Math.round(-11.5));
		
		System.out.println(Math.floor(11.4));
		System.out.println(Math.floor(-11.5));
		
		
	}
}
/***
 * float:    0.6710339
double:     0.04150553411984792
sum:        0.7125394529774224
quotient: 16.167336

 * 
 * 
 */
