package com.lsqt.components.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * 业务名:数据表格行对象
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
public class DataRow implements Iterable<Object>{
	/**
	 * 数据行单元格元素
	 */
	private List<Object> elements=new ArrayList<Object>(); 
	
	public DataRow(){};
	
	public DataRow(Object [] elements){
		List temp=Arrays.asList(elements);
		List row=new ArrayList(temp);
		this.elements=row;
	}
	
	public DataRow(List<Object> elements){
		this.elements= elements;
	}
	
	public void add(int index, Object e) {
		this.elements.add(index, e);
	}
	
	public boolean add(Object e) {
		return this.elements.add(e);
	}
	
	public Object get(int index) {
		return this.elements.get(index);
	}
	
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}
	
	public Iterator<Object> iterator() {
		return this.elements.iterator();
	}
	
	public Object remove(int index) {
		return this.elements.remove(index);
	}
	
	public boolean remove(Object e) {
		return this.elements.remove(e);
	}
	
	public int size() {
		return this.elements.size();
	}
	
	public Object[] toArray() {
		return this.elements.toArray(new Object[this.elements.size()]);
	}
	
	
	
}
