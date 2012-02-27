package com.lsqt.core.dao.hibernate.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装. 提供对象的深度克隆封装
 */
@SuppressWarnings("unchecked")
public final class BeanHelper extends org.apache.commons.beanutils.BeanUtils {
	/**
	 * 
	 */
	private static String GET="get";
	/**
	 * 
	 */
	private static String IS="is";
	/**
	 * 
	 */
	private static String HAS="has";
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(BeanHelper.class);
	/**
	 * 
	 */
	private BeanHelper() {}
	
	/**
	 * 封装org.apache.commons.beanutils.BeanUtils类copyProperties方法
	 * @param <T> 任意源目标对象类型
	 * @param <M> 任意目标对象类型
	 * @param dest 目标对象
	 * @param from 源目标对象
	 */
	public static <T,M> void copyPropertiesMethod(M dest,T from){
		try {
			copyProperties(dest, from);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
		} catch (InvocationTargetException e) {
			LOGGER.error(e);
		}
	}
	
	/**
	 * 数据转换方法
	 * @param <T> 任意源目标对象类型
	 * @param <M> 任意目标对象类型
	 * @param dest 目标对象
	 * @param from 源目标对象
	 * @return 存储源目标对象数据的目标对象
	 */
	public static <T,M> M dataConvert(M dest,T from){
		try {
			copyProperties(dest, from);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
		} catch (InvocationTargetException e) {
			LOGGER.error(e);
		}
		return dest;
	}
	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param object 目标对象
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param clazz 目标对象类
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	public static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);
	}
	
	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * @param object 目标对象
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象变量值
	 */
	public static Object forceGetProperty(Object object, String propertyName) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			LOGGER.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * @param object 目标对象
	 * @param propertyName 对象属性名称
	 * @param newValue 设置对应的对象属性新值
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			LOGGER.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}
	
	/**  
     * <pre>  
     * 以二进制方式深度克隆对象  ,源对象必须可以序列化
     * 如果从hibernte的一个session里取的对象 List<Entity>
     * </pre>  
     * @param src 被克隆的对象  
     * @return 克隆的对象  
     */  
	public static final Object copyObject(Object src) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(src);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object clone = in.readObject();
			in.close();
			return (clone);
		} catch (ClassNotFoundException e) {
			throw new InternalError(e.toString());
		} catch (StreamCorruptedException e) {
			throw new InternalError(e.toString());
		} catch (IOException e) {
			throw new InternalError(e.toString());
		}
	}
    
    /**
     * 通过反射获取对象属性值
     * @param target 目标对象
     * @return 返回对象的一系列属性值字符串,可在javaBean 中重写toString()方法时调用
     */
    public static String printProperyValue(Object target) {
    		StringBuffer toStringInfo=new StringBuffer("{");
		    	
		    	Method [] methods=target.getClass().getDeclaredMethods();
		    	Field [] fileds=target.getClass().getDeclaredFields();
		    	
		    	for(Method m: methods){
		    		for(Field f: fileds){
			    		String pojoMehodName=m.getName().toLowerCase();
			    		String pojoProperyName=f.getName().toLowerCase();
			    		
			    		int len=pojoMehodName.length();
			    		if(pojoMehodName.startsWith(GET)){
			    			pojoMehodName=pojoMehodName.substring(3, len);
			    			if(pojoMehodName.equals(pojoProperyName)){
			    				try {
									toStringInfo.append(f.getName()+"="+m.invoke(target, null)+" , ");
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
			    			}
			    		}
			    		if(pojoMehodName.startsWith(IS)){
			    			pojoMehodName=pojoMehodName.substring(2, len);
			    			if(pojoMehodName.equals(pojoProperyName)){
			    				try {
									toStringInfo.append(f.getName()+"="+m.invoke(target, null)+" , ");
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
			    			}
			    		}
			    		if(pojoMehodName.startsWith(HAS)){
			    			pojoMehodName=pojoMehodName.substring(3, len);
			    			if(pojoMehodName.equals(pojoProperyName)){
			    				try {
									toStringInfo.append(f.getName()+"="+m.invoke(target, null)+" , ");
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
			    			}
			    		}
		    		}
		    	}
		    	
		   toStringInfo.append("}");
    	return toStringInfo.toString();
    }
    
    /**
     * 
     * 方法说明：判断javabean是否不为null 
     *
     * @param obj javabean对象
     * @return 不为空，则返回真
     */
    public static boolean isNotNull(Object obj){
    	if(obj != null){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 
     * 方法说明：判断javabean是否为空
     *
     * @param obj javabean对象
     * @return 为空则返回真
     */
    public static boolean isNull(Object obj){
    	if(obj == null){
    		return true;
    	}else{
    		return false;
    	}
    }
}
