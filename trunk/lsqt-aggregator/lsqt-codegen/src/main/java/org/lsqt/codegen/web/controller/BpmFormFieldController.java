package org.lsqt.codegen.web.controller;

import java.util.List;
import java.util.Map;

import org.lsqt.codegen.web.support.DataSourceUtil;
import org.lsqt.codegen.web.support.DataTableUtil;
import org.lsqt.components.dao.dbutils.SqlExecutor;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Sky
 *
 */
@Controller
@RequestMapping("/bpmFormField")
public class BpmFormFieldController {
	
	private SqlExecutor sqlExecutor=new SqlExecutor(DataSourceUtil.getDataSource());
	
	@RequestMapping("index")
	public String index(){
		return "bpmformfield/list";
	}
	
	@RequestMapping("edit")
	public String edit(){
		return "bpmformfield/edit";
	}
	
	@RequestMapping("list/{id}")
	@ResponseBody
	public List list(@PathVariable  Long id){
		String sql="select B.tableName,A.* from bpm_form_field A inner join bpm_form_table B on A.tableId=B.tableId where A.tableId= ? " ;
		DataTable tb=sqlExecutor.executeQuery(sql,id);
		return tb.toList();
	}
}
