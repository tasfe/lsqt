package org.lsqt.components.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * 业务名:数据集对象
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
public class DataSet implements Iterable<DataTableTest>{
	public void setDataTables(List<DataTableTest> dataTables) {
		this.dataTables = dataTables;
	}

	/**数据表格**/
	private List<DataTableTest> dataTables=new ArrayList<DataTableTest>();
	
	public List<DataTableTest> getDataTables() {
		return dataTables;
	}

	/**输出参数**/
	private Object [] outputParams=null;
	
	public Object[] getOutputParams() {
		return outputParams;
	}

	public void setOutputParams(Object[] outputParams) {
		this.outputParams = outputParams;
	}
	
	public boolean add(DataTableTest dataTable) {
		return this.dataTables.add(dataTable);
	}

	public void add(int index, DataTableTest dataTable) {
		this.dataTables.add(index, dataTable);
		
	}

	public DataTableTest getDataTable(int index) {
		return this.dataTables.get(index);
	}

	public boolean isEmpty() {
		return this.dataTables.isEmpty();
	}

	public DataTableTest remove(int index) {
		return this.dataTables.remove(index);
	}

	public boolean remove(DataTableTest dataTable) {
		return this.dataTables.remove(dataTable);
	}
	
	public Iterator<DataTableTest> iterator() {
		return this.dataTables.iterator();
	}
}
