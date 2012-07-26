package com.hirisun.content.web.temptest.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceMonitor2 implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return proxy.invokeSuper(obj, args);
	}
	
	private Enhancer enhancer=new Enhancer();
	public Object getProxy(Class clazz){
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	public static void main(String args[]){
		
		ServiceMonitor2 m2=new ServiceMonitor2();
		MyxxxServiceImpl ser=(MyxxxServiceImpl)m2.getProxy(MyxxxServiceImpl.class);
		System.out.println(ser);
		ser.eating();
	}
}
