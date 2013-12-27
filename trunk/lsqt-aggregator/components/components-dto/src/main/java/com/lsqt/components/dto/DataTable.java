package com.lsqt.components.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
	/**
	 * 数据表格内,数据行集
	 */
	private List<DataRow> dataRow=new ArrayList<DataRow>();

	public boolean add(DataRow e) {
		return dataRow.add(e);
	}

	public void add(int index, DataRow e) {
		this.dataRow.add(index, e);
	}

	public DataRow getRow(int index) {
		return this.dataRow.get(index);
	}

	public boolean isEmpty() {
		return this.dataRow.isEmpty();
	}

	public DataRow remove(int index) {
		return this.dataRow.remove(index);
	}

	public boolean remove(DataRow e) {
		return this.dataRow.remove(e);
	}

	public int size() {
		return this.dataRow.size();
	}

	public Iterator<DataRow> iterator() {
		return this.dataRow.iterator();
	}
	
 
	public Collection<DataRow> getRows(){
		return this.dataRow;
	}
	
	public DataRow[] toArray() {
		return this.dataRow.toArray(new DataRow[this.dataRow.size()]);
	}
	
	
	
	/**
	 * 转化为二维数组
	 * @return	 
	public List<Object[]> toList(){
		List<Object[]> list=new ArrayList();
		DataRow[] row= toArray();
		for(DataRow  e: row){
			list.add(e.toArray());
		}
		return list;
	}
	*/
	
	/**
	public String toJson(){
		return "";
	}
	**/
	
	public static void main(String args[]){
		DataTable dt=new DataTable();
		
		
		DataRow row=new DataRow();
		row.add("1111");
		row.add("奔大本营");
		
		DataRow row2=new DataRow();
		row2.add("222");
		row2.add("奔大本营dsadfa");
		
		dt.add(row);
		dt.add(row2);
		
		DataSet ds=new DataSet();
		ds.add(dt);
		System.out.println(ds.size());
		
		for(DataRow i : dt.getRows()){
			System.out.println(i.get(1));
		}
		
	}
}
