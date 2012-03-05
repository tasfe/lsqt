package com.lsqt.core.dao.dbutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;


public class SqlExecutor {
	private static Properties properties;
	private static DataSource dataSource;
	
	static {
		try {
			properties = new Properties();
			properties.load(SqlExecutor.class.getResourceAsStream("/dbcpconfig.properties"));
			BasicDataSourceFactory b = new BasicDataSourceFactory();
			dataSource = b.createDataSource(properties);
		
		}catch(Exception e){
			try {
				throw new Exception("数据源配置错误或找不到数据库连接相关信息!");
			} catch (Exception e1) {
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
	
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * 批量执行SQL语句
	 * @param sql 带占位符的SQL语句
	 * @param dataTable 数据表格 
	 */
	public static boolean executeSql(String sql,Object [][] dataTable) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		return run.batch(sql, dataTable).length>0;
	}
	
	
	public static List<Object[]> executeSqlQuery(String sql) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		
		return  run.query(sql, new ArrayListHandler(){
			@Override
			protected Object[] handleRow(ResultSet rs) throws SQLException {
				return super.handleRow(rs);
			}
		});
	}
	
	
	public static List<Object[]> executeSqlQuery(String sql,Object [] paramValues) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		return run.query(sql,new ArrayListHandler(){
			@Override
			protected Object[] handleRow(ResultSet rs) throws SQLException {
				return super.handleRow(rs);
			}
		},paramValues);
	}
}
