package org.lsqt.components.dao.dbutil;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.lsqt.components.dao.suport.DataRow;
import org.lsqt.components.dao.suport.DataSet;
import org.lsqt.components.dao.suport.DataTable;
import org.lsqt.components.dao.suport.DbHelper;
import org.lsqt.components.dao.suport.ParamType;



/**
 * <pre>
 * 
 * 功能说明: 
 * 	  该执行器优先使用JNDI配置的数据源,其次才是jdbc数据源
 *    该SQL执行器,提供简易并直接的SQL语句操作数据库.
 *    该SQL执行器不依赖于任何第三方的数据源创建 
 *    (注:在多个service层内调用多个执行器,没有事务控制)
 * 
 * 编写日期:2011-4-21
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2012-3-10
 * 修改人：袁明敏
 * 修改内容：
 *    1.添加JNDI支持
 *    2.添加存储过程执行支持
 * </pre>
 */
public class SqlExecutor {

	/****/
	private static final Logger LOGGER = Logger.getLogger(SqlExecutor.class);
	/****/
	private static String USERNAME;
	/****/
	private static String PASSWORD;
	/****/
	private static String URL;
	/****/
	private static String DRIVERCLASSNAME;
	/****/
	private static String JNDINAME ;
	/****/
	private static DataSource dataSource;
	/****/
	private static boolean hasJndiDataSource;
	/****/
	private static Properties properties = new Properties();
	
	static {
		loadConfigFile();
		hasJndiDataSource=loadJndiDataSource();
		
		if(hasJndiDataSource==false){
			try {
				Class.forName(DRIVERCLASSNAME);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void loadConfigFile() {
		try {
			properties.load(SqlExecutor.class.getResourceAsStream("/dbconfig.properties"));
			LOGGER.debug(properties);
			
			USERNAME = properties.getProperty("username");
			PASSWORD = properties.getProperty("password");
			URL = properties.getProperty("url");
			DRIVERCLASSNAME = properties.getProperty("driverClassName");
			
			JNDINAME=properties.getProperty("jndiName");
			
		} catch (Exception e) {
			try {
				LOGGER.error("not found dataSource's configuration file ");
				throw new Exception(e.getMessage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static boolean loadJndiDataSource(){
		try {
			if(StringUtils.isNotEmpty(JNDINAME)){
				InitialContext context=new InitialContext();
				dataSource=(DataSource)context.lookup(JNDINAME);
			}
		} catch (NamingException e) {
			LOGGER.warn("have no JNDI config found");
			return false;
		}
		return false;
	}
	
	
	private static  Connection getConnection(){
		try{
			if(hasJndiDataSource){
				LOGGER.debug("database's connection from JNDI dataSource");
				return dataSource.getConnection();
			}else{
				Connection conn= DriverManager.getConnection(URL, USERNAME, PASSWORD);
				LOGGER.debug("database's connection from DriverManager");
				return conn;
			}
		}catch(SQLException e){
			LOGGER.debug(e);
			e.printStackTrace();
		}
		return null;
	}
	
	//-------------------------------------------------------------------------------
	/**
	 * 处理参数占位符字符串
	 * @param paramValues  paramValues
	 * @return processprocedureParamHold
	 */
	private static String processProcedureParamHold(Object[] paramValues) {
		String holdString = "";
		int holdLength = paramValues.length;
		for (int i = 0; i < holdLength; i++) {
			if (i != holdLength - 1) {
				holdString = "?," + holdString;
			} else {
				holdString = holdString + "?";
			}
		}
		LOGGER.debug(holdString);
		return holdString;
	}
	
	/**
	 * 执行存储过程,返回结果集
	 * @param procedureName 存储过程名称
	 * @return DataSet 
	 */
	public static DataSet executeProcedure(final String procedureName) {
		final DataSet DATASET = new DataSet();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "()}");

			boolean hasResults = cstmt.execute();

			while (hasResults) {
				DataTable dt = new DataTable();

				rs = cstmt.getResultSet();
				int colCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					DataRow row = new DataRow();
					for (int i = 0; i < colCount; i++) {
						row.add(rs.getObject(i + 1));
					}
					dt.add(row);
				}
				DATASET.add(dt);
				hasResults = cstmt.getMoreResults();
			}
		} catch (Exception e) {
			LOGGER.error("procedure execute fail ==> "+e.getMessage());
			return null;
		} finally {
			DbHelper.destroy(con, cstmt, rs);
		}

		return DATASET;
	}
	
	/**
	 * 执行只带输入参数的存储过程
	 * @param procedureName  存储过程名称
	 * @param paramValues 入参值
	 * @return DataSet 数据集
	 */
	public static DataSet executeProcedure(final String procedureName, final Object[] paramValues) {
		final DataSet DATASET = new DataSet();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + processProcedureParamHold(paramValues) + ")}");

			boolean hasResults = cstmt.execute();

			while (hasResults) {
				DataTable dt = new DataTable();

				rs = cstmt.getResultSet();
				int colCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					DataRow row = new DataRow();
					for (int i = 0; i < colCount; i++) {
						row.add(rs.getObject(i + 1));
					}
					dt.add(row);
				}
				DATASET.add(dt);
				hasResults = cstmt.getMoreResults();
			}
		} catch (Exception e) {
			LOGGER.error("procedure execute fail ==> "+e.getMessage());
			return null;
		} finally {
			DbHelper.destroy(con, cstmt, rs); 
		}
			
		return DATASET;
	}
	
	/**
	 * 存储过程调用 (返回结果集+输出参数+执行系列更新语句),调用时显示指定入参和输出参数据数据类型
	 * 
	 * @param procedureName 存储过程名称
	 * @param paramValues 入参值和输出参数的值
	 * @param paramValueTypes 储存过程入参和输出参数据的数据类型:ParamType.String待
	 * @return DataSet 数据集
	 * **/
	public static DataSet executeProcedure(final String procedureName, final Object[] paramValues, final int[] paramValueTypes) {
		if (paramValues.length != paramValueTypes.length) {
			LOGGER.error("procedure execute fail, Parameters and parameter types value , the length not equal  ");
			return null;
		}

		final DataSet DATASET = new DataSet();

		String sqlHold = processProcedureParamHold(paramValues);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con=null;
		try {
			con=getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + sqlHold + ")}");

			List<Integer> outputParamIndex = new ArrayList<Integer>(); 
			for (int i = 0; i < paramValues.length; i++) {
				if (paramValues[i] == ParamType.HOLDER) {
					cstmt.registerOutParameter(i + 1, paramValueTypes[i]);
					outputParamIndex.add(i + 1);
				} else {
					cstmt.setObject(i + 1, paramValues[i]);
				}
			}

			boolean hasResults = cstmt.execute();

			// 处理输出参数值
			List<Object> outputValues = new ArrayList<Object>();
			for (int i = 0; i < outputParamIndex.size(); i++) {
				outputValues.add(cstmt.getObject(outputParamIndex.get(i)));
			}

			DATASET.setOutputParams(outputValues.toArray(new Object[outputValues.size()]));

			while (hasResults) {
				DataTable dt = new DataTable();

				rs = cstmt.getResultSet();// ---取得第一个结果集
				int colCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					DataRow row = new DataRow();
					for (int i = 0; i < colCount; i++) {
						row.add(rs.getObject(i + 1));
					}
					dt.add(row);
				}
				DATASET.add(dt);
				hasResults = cstmt.getMoreResults();
			}
		} catch (Exception e) {
			LOGGER.error("execute procedure fail ==> "+e.getMessage());
			return null;
		} finally {
			DbHelper.destroy(con, cstmt, rs); 
		}
		return DATASET;
	}
	
	
	
	/**
	 * 执行更新的SQL语句
	 * @param sql
	 * @return
	 */
	public static boolean executeSql(String sql){
		QueryRunner run = new QueryRunner();
		Connection conn=getConnection();
		try{
			return run.update(conn,sql)>0;
		}catch(SQLException ex){
			LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return false;
		}finally{
			DbHelper.destroy(conn, null, null);
		}
	}
	
	public static boolean executeSql(String sql,Object[] paramValues){
		QueryRunner run = new QueryRunner();
		Connection conn=getConnection();
		try{
			return run.update(conn,sql,paramValues)>0;
		}catch(SQLException ex){
			LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return false;
		}finally{
			DbHelper.destroy(conn, null, null);
		}
	}
	
	/**
	 * 批量执行SQL更新语句
	 * @param sql 带占位符的SQL语句
	 * @param dataTable 参数数据表格 
	 */
	public static boolean executeSql(String sql,Object [][] dataTable){
		QueryRunner run = new QueryRunner();
		Connection conn=getConnection();
		try{
			
		  return run.batch(conn,sql, dataTable).length>0;
		  
		} catch(SQLException ex){
			LOGGER.debug("execute sql fail ==> "+ex.getMessage());
			return false;
		}
		finally {
		    DbHelper.destroy(conn, null, null);
		}
	}

	/**
	 * 执行查询语句,返回二维结果集
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> executeSqlQuery(String sql){
		
		QueryRunner run = new QueryRunner();
		Connection conn = getConnection();
		try {
			return  run.query(conn,sql, new ArrayListHandler(){
				@Override
				protected Object[] handleRow(ResultSet rs) throws SQLException {
					return super.handleRow(rs);
				}
			});
		} catch (SQLException ex) {
			LOGGER.error("execute sql fail ==> " + ex.getMessage());
			return null;
		} finally {
			DbHelper.destroy(conn, null, null);
		}
	}

	/**
	 * 执行带"?"占位符的查询语句,返回二维结果集
	 * @param sql
	 * @param paramValues
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> executeSqlQuery(String sql,Object [] paramValues){
		QueryRunner run = new QueryRunner( );
		Connection conn = getConnection();
		try{
		return run.query(conn,sql,new ArrayListHandler(){
			@Override
			protected Object[] handleRow(ResultSet rs) throws SQLException {
				return super.handleRow(rs);
			}
		},paramValues);
		}catch(SQLException ex){
			LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return null;
		}finally{
			 DbHelper.destroy(conn, null, null);
		}
	}
}


