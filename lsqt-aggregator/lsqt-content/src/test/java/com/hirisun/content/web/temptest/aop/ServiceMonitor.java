package com.hirisun.content.web.temptest.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.MethodInterceptor;

public class ServiceMonitor implements InvocationHandler{
	private Object obj=null;
	
	public ServiceMonitor(Object obj){
		this.obj=obj;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		long cntBegin=System.currentTimeMillis();
		
		Object rs=method.invoke(this.obj, args);
		
		
		long cntEnd=System.currentTimeMillis();
	
		System.out.println("方法执行时长为："+Math.abs(cntBegin-cntEnd));
		return rs;
	}
	
	public static void main(String arsg[]){
		MyxxxService s=new MyxxxServiceImpl();
		ServiceMonitor m=new ServiceMonitor(s);  //横切逻辑代码实现在ServiceMonitor类中
		MyxxxService ttt=	(MyxxxService)Proxy.newProxyInstance(s.getClass().getClassLoader(),  s.getClass().getInterfaces(), m);
		ttt.eating();
	
		
	}
}
