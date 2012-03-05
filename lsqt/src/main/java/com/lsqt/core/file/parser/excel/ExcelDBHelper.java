package com.lsqt.core.file.parser.excel;

import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;


public final class ExcelDBHelper {
	private static Properties properties;
	private static DataSource dataSource;
	
	static {
		try {
			properties = new Properties();
			properties.load(ExcelDBHelper.class.getResourceAsStream("/dbcpconfig.properties"));
			BasicDataSourceFactory b = new BasicDataSourceFactory();
			dataSource = b.createDataSource(properties);
		
		}catch(Exception e){
			try {
				throw new ExcelException("Excel导入所需数据源配置错误或找不到数据库连接相关信息!");
			} catch (ExcelException e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	public static boolean hasDataSource(){
		if(dataSource==null){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean batch(String sql,Object [][] dataTable) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		return run.batch(sql, dataTable).length>0;
	}
	
	 
	
}
