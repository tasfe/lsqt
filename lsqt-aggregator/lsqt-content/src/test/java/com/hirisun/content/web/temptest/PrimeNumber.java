package com.hirisun.content.web.temptest;

public class PrimeNumber {
	
	public  PrimeNumber(){
		
	}
	

	//求100内的所有素数（质数）
    public static void main(String[] args) {
    	System.out.println(mytest());
    }
    
    public static int mytest(){
    	try{
			System.out.println("aaa");
			int a1=1, b1=0;
			System.out.println(a1/b1);
		
		}catch(Exception e){
			System.out.println("bbb"+e.getMessage());
			return -1;
		}finally{
			System.out.println("ccc");
			return -3;
		}
    	//return -4;
    }
}

class A{
	final public void func(){
		System.out.println("A..func....");
	}
}

class B extends A{
	
}

interface My{
	abstract void test();
}
