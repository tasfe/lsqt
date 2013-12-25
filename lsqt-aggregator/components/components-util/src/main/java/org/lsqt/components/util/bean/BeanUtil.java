package org.lsqt.components.util.bean;


import java.lang.reflect.Field;

/**
 * 
 */
public final class BeanUtil  {

	
	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param object 目标对象
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	private static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
		 
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param clazz 目标对象类
	 * @param propertyName 目标对象属性名称
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 * @return 返回对象的"域"对象
	 */
	private static Field getDeclaredField(Class clazz, String propertyName) throws NoSuchFieldException {
	
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
	public static Object forceGetProperty(Object object, String propertyName) throws RuntimeException {
		
		
		try {
			Field field = getDeclaredField(object, propertyName);

			boolean accessible = field.isAccessible();
			field.setAccessible(true);

			Object result = null;

			result = field.get(object);
			field.setAccessible(accessible);
			return result;
		} catch (Exception e) {
			//LOGGER.info("error  happened at #forceGetProperty");
			throw new RuntimeException(e.getMessage());
		}
	}

	/**    
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * @param object 目标对象
	 * @param propertyName 对象属性名称
	 * @param newValue 设置对应的对象属性新值
	 * @throws NoSuchFieldException 如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName, Object newValue) {
		
		try {
		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		
		field.set(object, newValue);
		field.setAccessible(accessible);
		} catch (Exception e) {
			//LOGGER.info("Error won't happen");
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
    
}
