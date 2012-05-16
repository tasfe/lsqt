package org.lsqt.components.dao.suport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <pre>
 * 业务名:数据库查询出来的数据集对象
 * 功能说明:
 * 编写日期:2011-4-21
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2011-4-21
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
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
