package org.lsqt.components.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		return temp;
	}
	
	public Map<String, Object> getScalarRowMap() {
		return this.getRowMap(0);
	}
	
	
	@SuppressWarnings("rawtypes")
	public  List<Map> toMapList(){
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
}

