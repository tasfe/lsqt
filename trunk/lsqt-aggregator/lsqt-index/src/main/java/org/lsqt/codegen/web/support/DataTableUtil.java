package org.lsqt.codegen.web.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


public class DataTableUtil {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(Page page){
		Map map=new LinkedHashMap();
		map.put("total", page.getTotalRecord());
		
		map.put("data", toList(page.getDataTable()));
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> toList(DataTable table){
		Object[] head=table.getDataHead();
		Object [][] body=table.getDataBody();
		
		List<Map> list=new ArrayList<Map>();
		for(Object[] row: body){
			Map<Object,Object> temp=new LinkedHashMap<Object,Object>();
			for(int i=0;i<row.length;i++){
				temp.put(head[i],  row[i]);
			}
			list.add(temp);
		}
		return list;
	}
	
}
