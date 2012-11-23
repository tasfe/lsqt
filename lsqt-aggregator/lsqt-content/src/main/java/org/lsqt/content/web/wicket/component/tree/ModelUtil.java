package org.lsqt.content.web.wicket.component.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModelUtil {

	
 
	/**
	 * 
	 * @param beans 
	 * @param property
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static List<String[]> convertBean(List<?> beans,String [] property) throws NoSuchFieldException,
			IllegalAccessException {
		
		
		List<String[]> list = new ArrayList<String[]>();
		
		for (Object i : beans) {
			Object idPropertyValue = getBeanPropertyValue(i, property[0]); // parent
			Object pidPropertyValue = getBeanPropertyValue(i, property[1]); // child
			Object displayPropertyValue = getBeanPropertyValue(i, property[2]); // lable
			//Object urlPropertyValue=getBeanPropertyValue(i, urlProperty); // url
			if (null != idPropertyValue && null != pidPropertyValue) {

				String[] row = new String[] {
						String.valueOf(idPropertyValue),
						String.valueOf(pidPropertyValue),
						null == displayPropertyValue ? "" : String.valueOf(displayPropertyValue)
						//null == urlPropertyValue ? "" : String.valueOf(urlPropertyValue)
				};

				list.add(row);
			} else {
				throw new NullPointerException(
						"SimpleTree#build() error, parentId and childId must be not null,the root item must be pid=id !");
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param obj
	 * @param property
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private static  Object getBeanPropertyValue(Object obj,String property) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		Class clazz=obj.getClass();
		Field field =clazz.getDeclaredField(property);
		
		boolean isAccess=field.isAccessible();
		
		field.setAccessible(true);
		Object value=field.get(obj);
		field.setAccessible(isAccess);
		
		//System.out.println(property+" : "+value);
		return value;
	}
}
