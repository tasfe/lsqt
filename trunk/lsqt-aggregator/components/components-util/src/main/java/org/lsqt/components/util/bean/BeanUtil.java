package org.lsqt.components.util.bean;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * 功能说明: 
 * 	 
 * 
 * 编写日期:2011-4-21
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2012-3-10
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 */
public class BeanUtil  {

	
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
	
	/**
	 * 获取pojo的filed名称与method名称的映射
	 * @param clazz
	 * @param isGetter
	 * @return
	
	@SuppressWarnings("rawtypes")
	public static  Map<String,String> getSetterGetterMap(Class clazz,boolean isGetter){
		
		Set<String> fieldSet=new LinkedHashSet<String>();
		Set<String> boolFieldSet=new LinkedHashSet<String>();
		
		Set<String> methodSet=new LinkedHashSet<String>();
		Set<String> boolmethodSet=new LinkedHashSet<String>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field e:superClass.getDeclaredFields()){
				
				if(e.getGenericType().equals(boolean.class)){
					boolFieldSet.add(e.getName());
				}else{
					fieldSet.add(e.getName());
				}
			}
			
			for(Method m: superClass.getDeclaredMethods()){ 
				if(isGetter){
					
					if(m.getParameterTypes()!=null && m.getParameterTypes().length==0 &&
							(!m.getReturnType().equals(void.class)) ){//判断getter 方法：无入参，有返回值
						
						if ( m.getReturnType().equals(boolean.class)) { 
							boolmethodSet.add(m.getName());
						}else{
							methodSet.add(m.getName());
						}
					}
				}else{
				
					if(m.getReturnType().equals(void.class) && m.getParameterTypes().length ==1){//判断setter 方法：只有一个入参，无返回值
						if ( !(m.getParameterTypes()[0].equals(boolean.class))) { 
							methodSet.add(m.getName());
						}else{
							boolmethodSet.add(m.getName());
						}
					}
				}
				
			}
		}

		
		//找到每个字段的setter方法.  (注：isXXX开头的boolean字段,其setter方法为 setXXX)
		Map<String,String> map=new LinkedHashMap<String,String>();
		if(isGetter==false){
			for(String f:fieldSet){
				for(String m:methodSet){
					if(("set"+f).equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
				}
			}
			
			//example:isBad ==> setBad , msTool ==> setMsTool
			for(String f:boolFieldSet){
				for(String m:boolmethodSet){
					String setM="set"+f.substring(2, f.length()); //针对isXXX属性
					if(setM.equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
					
					if(("set"+f).equalsIgnoreCase(m)){ 	// 针对非isXXX属性
						map.put(f, m);
						break;
					}
					
				}
			}
			System.out.println(clazz.getName()+"'s  setter are, size is "+map.keySet().size()+" :  "+map);
		}else{
			
			//isBad ==> isBad , msTool ==> isMsTool , hasTool ==> isHasTool
			for(String f:fieldSet){
				for(String m:methodSet){
					if(("get"+f).equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
				}
			}
			
			for(String f:boolFieldSet){
				for(String m:boolmethodSet){
					if(f.equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
					
					if(f.equalsIgnoreCase(m.substring(2,m.length()))){
						map.put(f, m);
						break;
					}
				}
			}
			System.out.println(clazz.getName()+"'s  getter are, size is "+map.keySet().size()+" :  "+map);
		}
		return map;
	}
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	public static  Map<Field,Method> getSetterGetterMap(Class clazz,boolean isGetter){
		Map<Field,Method> setterGetter=new LinkedHashMap<Field,Method>();

		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field f:superClass.getDeclaredFields()){
				Method m=getMethodByField(superClass,isGetter,f);
				if(m!=null){
					setterGetter.put(f, m);
				}
			}
			
		}
		
		for(Field f: setterGetter.keySet()){
			System.out.println(f.getName()+"  "+setterGetter.get(f).getName());
		}
		
		return setterGetter;
	}
	
	/**
	 * 跟据域，找到对应的getter/setter方法
	 * @param clazz
	 * @param getterSetterType
	 * @param field
	 * @return
	 */
	@SuppressWarnings({"rawtypes" })
	private static Method getMethodByField(Class clazz,boolean isGetter,Field field){
		for(Field e:clazz.getDeclaredFields()){
			if(field.getName().equals(e.getName())){
				if(isGetter==false){
					if(! e.getGenericType().equals(boolean.class)){ //处理非布尔值的域
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanSetter(m) && ("set".concat(e.getName())).equalsIgnoreCase(m.getName())){
								return m;
							}
						}
					}else{
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanSetter(m)){
								if(e.getName().startsWith("is") 
										&& ("set".concat(e.getName().substring(2,e.getName().length()))).equalsIgnoreCase(m.getName()) ){
									return m;
								}else{
									if(("set".concat(e.getName())).equalsIgnoreCase(m.getName())){
										return m;
									}
								}
							}
						}
					}
					
				}else if(isGetter){
					if(! e.getGenericType().equals(boolean.class)){ //处理非布尔值的域
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanGetter(m) && "get".concat(e.getName()).equalsIgnoreCase(m.getName())){
								return m;
							}
						}
					}else{
						for(Method m: clazz.getDeclaredMethods()){
							if(isCanGetter(m)){
								if(e.getName().startsWith("is") && e.getName().equalsIgnoreCase(m.getName())){
									return m;
								}else{
									if(("is".concat(e.getName()).equalsIgnoreCase(m.getName()))){
										return m;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 粗略判断此方法是否是setter方法
	 * @param m
	 * @return
	 */
	public static boolean isCanSetter(Method m){
		// 判断setter:只有一个入参，方法名以set开头,无返回值，
		return (m!=null 
				&& m.getName().startsWith("set")
				&& m.getReturnType().equals(void.class)
				&& m.getParameterTypes().length == 1) ;
	}

	/**
	 * 粗略判断此方法是否是getter方法
	 * @param m
	 * @return
	 */
	public static boolean isCanGetter(Method m){
		// 判断getter方法：无入参，有返回值
		return (m!=null
				&& m.getParameterTypes().length == 0
				&& (!m.getReturnType().equals(void.class)));
	}

}
