package org.lsqt.components.dto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
	 *  设置表格头部显示的javaBean属性
	 * @param props -
	 */
	public DataTable toSettingPropety(String ... props){
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
	
	public List<Object[]> toBeanList(Class ... clazzes ){

		for(Class e:clazzes){
			Field[] fields=e.getDeclaredFields();
			Method[] methods=e.getDeclaredMethods();
			
			for(Field f:fields){
				
				//配置getter与setter方法， 注意:is,has,have开头的属性返回值为boolean值才为合法getter,setter
				if(f.getName().equalsIgnoreCase("")){
					
				}
			}
		}
		return null;
	}
	
	public List<Map<String,Object>> toMapList(){
		return null;
	}
	
	public String toJSON(){
		return null;
	}
	
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

