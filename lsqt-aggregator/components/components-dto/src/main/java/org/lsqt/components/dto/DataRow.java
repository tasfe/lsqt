package org.lsqt.components.dto;

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
	
	private List<Object> rowValues=new ArrayList<Object>(); 
	private List<String> rowNames=new ArrayList<String>(); 
	
	public DataRow(){};
	
	public DataRow(Object [] elements){
		List<Object> temp=Arrays.asList(elements);
		List<Object> row=new ArrayList<Object>(temp);
		this.rowValues=row;
	}
	
	public DataRow(List<Object> elements){
		this.rowValues= elements;
	}
	
	
	public boolean add(Object element) {
		return this.rowValues.add(element);
	}

	public void add(String label,Object element) {
		this.rowValues.add(element);
		this.rowNames.add(label);
	}
	
	public Object get(int index){
		return this.rowValues.get(index);
	}
	
	public Object get(String label){
		int index=0;
		for(int i=0;i<this.rowNames.size();i++){
			if(label.equals(this.rowNames.get(i))){
				index=i;
				break;
			}
		}
		if(this.rowNames.contains(label) && index<=this.rowValues.size()-1){
			return this.rowValues.get(index);
		}
		return null;
	}
	
	public boolean isEmpty() {
		return this.rowValues.isEmpty();
	}
	
	public Iterator<Object> iterator() {
		return this.rowValues.iterator();
	}
	
	public Object remove(int index) {
		return this.rowValues.remove(index);
	}
	
	public boolean remove(Object e) {
		return this.rowValues.remove(e);
	}

	public Object[] toArray() {
		return this.rowValues.toArray(new Object[this.rowValues.size()]);
	}
	
}
