package org.lsqt.components.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class DataTableTest {
	

	@Test
	public void testToBeanList(){
		

		test(CollegeStudentWoman.class);
		test(CollegeStudentMan.class);
		
		DataTable tb=new DataTable();

		DataRow r1=new DataRow();
		r1.add("id", 1);
		r1.add("havingSex", true);
		r1.add("hadSex", true);
		r1.add("isSex", true);
		r1.add("name", "男大学生");
		r1.add("collegeName","男子大学");
		r1.add("hasSex",true);
		r1.add("haveSex", true);
		r1.add("car", new Car(1001));
		
		r1.add("id", 10001);
		r1.add("name","女大学生");
		r1.add("collegeName","女子大学");
		r1.add("reallyManDesc","yes, i'm man!!");
		
		tb.add(r1);
		
		
		
		tb.doSettingPropety(
				"id", "havingSex", "hadSex", "isSex", "name","collegeName", "hasSex", "haveSex", "car", 
				"id","name","reallyManDesc");
		tb.toBeanList(CollegeStudentWoman.class,CollegeStudentMan.class);
		
		
		
		
		tb.doSettingPropety(
				"id", "havingSex", "hadSex", "isSex", "CollegeStudentMan.name","collegeName", "hasSex", "haveSex", "car", 
				"id","CollegeStudentWoman.name","reallyManDesc");
		//tb.toBeanList(CollegeStudentWoman.class,CollegeStudentMan.class);
	}
	
	@SuppressWarnings("rawtypes")
	private void test(Class clazz){

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
		/*
		System.out.println(fieldSet);
		System.out.println(boolFieldSet);
		
		System.out.println(methodSet);
		System.out.println(boolmethodSet);
		*/
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
		System.out.println(clazz.getName()+"'s  setter are, size is "+map.keySet().size()+" :  "+map);
	}
}
