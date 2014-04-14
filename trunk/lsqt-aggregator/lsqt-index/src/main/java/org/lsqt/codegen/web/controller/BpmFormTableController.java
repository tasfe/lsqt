package org.lsqt.codegen.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.lsqt.codegen.web.model.BpmFormTable;
import org.lsqt.codegen.web.model.SysDataSource;
import org.lsqt.codegen.web.support.DataTableUtil;
import org.lsqt.codegen.web.support.DataSourceUtil;
import org.lsqt.components.dao.dbutils.SqlExecutor;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/bpmFormTable")
public class BpmFormTableController {
	
	private SqlExecutor sqlExecutor=new SqlExecutor(DataSourceUtil.getDataSource());
	
	
	@RequestMapping("index")
	public String index(){
		return "bpmformtable/list";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "bpmformtable/edit";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public void save(String data){
		JSONObject obj=JSON.parseObject(data);
		BpmFormTable tb=JSONObject.toJavaObject(obj,BpmFormTable.class);
		this.sqlExecutor.entitySaveOrUpdate(tb);
	}
	
	@RequestMapping("batchSave")
	@ResponseBody
	public void batchSave(String data){
		System.out.println(data);
		List<BpmFormTable> list=JSONArray.parseArray(data, BpmFormTable.class);
		for(BpmFormTable t: list){
			this.sqlExecutor.entitySaveOrUpdate(t);
		}
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Map list(Integer pageSize,Integer pageIndex){
		Page page=null;
		if(pageSize!=null && pageIndex!=null){
			page=new Page(pageSize,pageIndex+1);
		}else{
			page=new Page(20,1);
		}
		
	//	sqlExecutor.executeQueryPage(page, "SELECT B.name dsName, A.tableId,A.tableName,A.entityName,A.tableDesc,A.versionNo,A.isPublished,A.isGen,A.publishedBy,date_format(A.publishTime,'%Y-%m-%d') publishTime FROM bpm_form_table A  inner join sys_datasource B on A.dsId=B.id");
		
	//	return DataTableUtil.toMap(page);
		return null;
	}
	
	@RequestMapping("get/{id}")
	@ResponseBody
	public Map get(@PathVariable  Long id){
		DataTable tb=sqlExecutor.executeQuery("select tableId,tableName,entityName,tableDesc,versionNo,isPublished,isGen,publishedBy,publishTime from bpm_form_table where tableId=?",id);
		return tb.getScalarRowMap();
	}
	
	
}
