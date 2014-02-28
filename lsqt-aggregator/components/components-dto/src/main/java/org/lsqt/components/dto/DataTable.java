package org.lsqt.components.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * 业务名:数据表格对象
 * 功能说明:
 * 编写日期:2013-12-25
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2013-12-25
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 */
public class DataTable {
	
	private Object[] dataHead;  //默认来自数据库meta column label
	private Object[][] dataBody;  //数据体
	
	private DataTable(){}
	
	public DataTable(Object [] dataHead,Object[][] dataBody){
		this.dataHead=dataHead;
		this.dataBody=dataBody;
	}
	
	public Object[][] getDataBody(){
		return this.dataBody;
	}
	
	public Object [] getDataHead(){
		return this.dataHead;
	}
	
	/**
	 * 获取指定索引的行数据
	 * @param rowIndex
	 * @return
	 */
	public Map<String, Object> getRowMap(int rowIndex) {
		Map temp = new HashMap();
		Object[] rs = null;
		if (dataHead != null && dataBody != null) {
			int rowCnt = 0;
			for (Object[] row : dataBody) {
				rowCnt++;
			}
			if ((rowIndex + 1) > rowCnt) {
				return temp;
			}

			int idx = 0;
			for (Object[] row : dataBody) {
				if (idx == rowIndex) {
					rs = row;
				}
				idx++;
			}
		}

		if (rs != null && dataHead != null) {
			for(int i=0;i<dataHead.length;i++){
				temp.put(dataHead[i],rs[i]);
			}
		}
		System.out.println(temp);
		return temp;
	}
	
	public Map<String, Object> getScalarRowMap() {
		return this.getRowMap(0);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	public  List<Map> toList(){
		List<Map> list=new LinkedList<Map>();
		for(Object[] row: this.dataBody){
			Map<Object,Object> temp=new LinkedHashMap<Object,Object>();
			for(int i=0;i<row.length;i++){
				temp.put(this.dataHead[i],  row[i]);
			}
			list.add(temp);
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 如果数据表格有设置bean的属性字段则优先使用表格属性字段的设置；
	 * 如果没有，则按照行对象的属性字段设置值
	 *  
	 * @param clazzes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List toBeanList(Class ... clazzes ){
		List list=new ArrayList();
		
		List<String> holdedProps=new ArrayList<String>();
		for(Class e:clazzes){
			Map<String,String> setterMap=getSetterMap(e); 
			
		}
		return list;
	}
	
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
	**/
	


	/**
	 * 处理每一个bean，
	 * @param clazz
	 */
	private Map<String,String> getSetterMap(Class<?> clazz){

		Set<String> fieldSet=new HashSet<String>();
		Set<String> boolFieldSet=new HashSet<String>();
		
		Set<String> methodSet=new HashSet<String>();
		Set<String> boolmethodSet=new HashSet<String>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field e:superClass.getDeclaredFields()){
				
				if(e.getGenericType().equals(boolean.class)){
					boolFieldSet.add(e.getName());
				}else{
					fieldSet.add(e.getName());
				}
				
				
			}
			
			for(Method m: superClass.getDeclaredMethods()){ 
				if(m.getReturnType().equals(void.class) && m.getParameterTypes().length ==1){//判断setter 方法：只有一个入参，无返回值
					if ( !(m.getParameterTypes()[0].equals(boolean.class))) { 
						methodSet.add(m.getName());
					}else{
						boolmethodSet.add(m.getName());
					}
				}
				
			}
		}
		
		
		//找到每个字段的setter方法.  (注：isXXX开头的boolean字段,其setter方法为 setXXX)
		Map<String,String> map=new HashMap<String,String>();
		for(String f:fieldSet){
			for(String m:methodSet){
				if(("set"+f).equalsIgnoreCase(m)){
					map.put(f, m);
					break;
				}
			}
		}
		
		for(String f:boolFieldSet){
			for(String m:boolmethodSet){
				if(f.startsWith("is")){
					String temp="set"+f.substring(2, f.length());
					if(temp.equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
				}else{
					if(("set"+f).equalsIgnoreCase(m)){
						map.put(f, m);
						break;
					}
				}
			}
		}
		//System.out.println(map);
		
		return map;
		
	}

}

