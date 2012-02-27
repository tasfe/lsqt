package com.lsqt.core.dao.hibernate.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 存储过程数据输出类,兼带输出参数加输出结果集.或两者都有之
 * @author 袁明敏
 *
 */
public class DataSet implements IDataResultSet<DataTable>{
	/**数据表格**/
	private List<DataTable> dataTables=new ArrayList<DataTable>();
	
	/**输出参数**/
	private Object [] outputParams=null;
	
	public Object[] getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(Object[] outputParams) {
		this.outputParams = outputParams;
	}

	public DataTable[] toArray() {
		return this.dataTables.toArray(new DataTable[this.dataTables.size()]);
	}

	public boolean add(DataTable dataTable) {
		return this.dataTables.add(dataTable);
	}

	public void add(int index, DataTable dataTable) {
		this.dataTables.add(index, dataTable);
		
	}

	public DataTable get(int index) {
		return this.dataTables.get(index);
	}

	public boolean isEmpty() {
		return this.dataTables.isEmpty();
	}

	public DataTable remove(int index) {
		return this.dataTables.remove(index);
	}

	public boolean remove(DataTable dataTable) {
		return this.dataTables.remove(dataTable);
	}

	public int size() {
		return this.dataTables.size();
	}

	public Iterator<DataTable> iterator() {
		return this.dataTables.iterator();
	}
}
