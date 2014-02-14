package org.lsqt.components.dto;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
public class DataTable implements Iterable<DataRow>{
	
	/*** 数据表格行集  **/
	private List<DataRow> dataRows=new ArrayList<DataRow>();
	
	/*** 数据表格显示的头部 **/
	private List<String> settingNames=new ArrayList<String>();
	
	/*** 数据表格显示的头部javaBean 属性 **/
	private List<String> settingProperty=new ArrayList<String>();
	

	/**
	 * 设置表格头部名称
	 * @param names -
	 */
	public DataTable toSettingName(String ... names){
		this.settingNames=new ArrayList(Arrays.asList(names));
		return this;
	}
	
	/**
	 *  设置表格头部显示的javaBean属性，属性名不区分大小写
	 * @param props -
	 */
	public DataTable doSettingPropety(String ... props){
		this.settingProperty=new ArrayList(Arrays.asList(props));
		return this;
	}


	public boolean add(DataRow e) {
		return dataRows.add(e);
	}

	public void add(int index, DataRow e) {
		this.dataRows.add(index, e);
	}

	public DataRow getDataRow(int index) {
		return this.dataRows.get(index);
	}

	public boolean isEmpty() {
		return this.dataRows.isEmpty();
	}

	public DataRow remove(int index) {
		return this.dataRows.remove(index);
	}

	public boolean remove(DataRow e) {
		return this.dataRows.remove(e);
	}

	public Iterator<DataRow> iterator() {
		return this.dataRows.iterator();
	}
	
	public List<DataRow> getDataRows(){
		return this.dataRows;
	}
	
	public void setDataRows(List<DataRow> dataRows) {
		this.dataRows = dataRows;
	}

	
	/**
	 * 数据表格返回二维结果集.
	 * @return
	 */
	public Object[][] toArrays() {
		Object [][] rs=new Object[this.dataRows.size()][];
		for(int row=0;row<this.dataRows.size();row++){
			rs[row]=this.dataRows.get(row).toArray();
		}
		return rs;
	}
	
	/**
	 * 数据表格返回二维结果集.
	 * @return
	 */
	public List<Object[]> toArrayList(){
		List<Object[]> list=new ArrayList<Object[]>();
		for(DataRow row:this.dataRows){
			list.add(row.toArray());
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
	public List toBeanList(Class ... clazzes ){
		
		List<String> holdedProps=new ArrayList<String>();
		for(Class e:clazzes){
			Map<String,String> setterMap=getGetterSetterMap(e); 
			
		}
		//System.out.println(holdedProps);
		return null;
	}
	
	/**
	 * 处理每一个bean，
	 * @param clazz
	 * @param holdedIndexs
	 */
	private Map<String,String> getGetterSetterMap(Class<?> clazz){

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Map<String,Object>> toMapList(){
		return null;
	}
	/*
	public String toJSON(){
		return null;
	}
	*/
	
	/**
	 * <pre>
	 * &ltdataTable&gt
	 * 	&ltdataRow&gt
	 * 		&ltobject&gt
	 * 			&ltname>birthday&lt/name&gt
	 * 			&ltvalue>2013-10-20 00:00:00&lt/name&gt
	 * 		&lt/object&gt
	 * 	&lt/dataRow&gt
	 * &lt/dataTable&gt
	 * </pre>
	 * @return
	 */
	public String toXML(){
		return null;  
	}
	
	public String toString(){
		StringBuilder info=new StringBuilder();
		for(DataRow r: this.dataRows){
			info.append(Arrays.asList(r.toArray()));
		}
		return info.toString();
	}
}

