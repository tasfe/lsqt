package org.lsqt.codegen.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.lsqt.codegen.web.model.SysDataSource;
import org.lsqt.codegen.web.support.DataSourceUtil;
import org.lsqt.components.dao.dbutils.SqlExecutor;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@Controller
@RequestMapping("/sysDataSource")
@SuppressWarnings({"rawtypes","unchecked"})
public class SysDataSourceController {
	
	private SqlExecutor sqlExecutor=new SqlExecutor(DataSourceUtil.getDataSource());
	
	
	@RequestMapping("index")
	public String index(){
		return "sysdatasource/list";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "sysdatasource/edit";
	}
	
	@RequestMapping("listCombobox")
	@ResponseBody
	public List listCombobox(){
		DataTable dataTable=sqlExecutor.executeQuery("select id,name text from sys_datasource");
		return dataTable.toMapList();
	}
	
	
	@RequestMapping("save")
	@ResponseBody
	public void save(String data){
		JSONObject obj=JSON.parseObject(data);
		SysDataSource ds=JSONObject.toJavaObject(obj,SysDataSource.class);
		this.sqlExecutor.entitySaveOrUpdate(ds);
	}

	@RequestMapping("get/{id}")
	@ResponseBody
	public SysDataSource get(@PathVariable  Long id){
		return sqlExecutor.entityGetById(SysDataSource.class,id);
	}
	
	@RequestMapping("delete/{ids}")
	@ResponseBody
	public void delete(@PathVariable  String ids){
		sqlExecutor.entityDeleteByIds(SysDataSource.class, ids.split(","));
	}
	

	@RequestMapping("list")
	@ResponseBody
	public Map list(Integer pageSize,Integer pageIndex){
		Page page=null;
		if(pageSize!=null && pageIndex!=null){
			page=new Page(pageSize,pageIndex+1);
		}else{
			page=Page.getDefaultPage();
		}
		sqlExecutor.entityQueryPage(page,SysDataSource.class);
		Map map=new LinkedHashMap();
		map.put("total",page.getTotalRecord());
		map.put("data", page.getEntityTable());
		return map;
	}
	
}
