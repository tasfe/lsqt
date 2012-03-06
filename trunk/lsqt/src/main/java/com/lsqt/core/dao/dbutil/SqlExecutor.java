package com.lsqt.core.dao.dbutil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.jdbc.Work;

import com.lsqt.core.dao.hibernate.exception.DaoException;
import com.lsqt.core.dao.hibernate.impl.DataRow;
import com.lsqt.core.dao.hibernate.impl.DataSet;
import com.lsqt.core.dao.hibernate.impl.DataTable;
import com.lsqt.core.dao.hibernate.impl.DbHelper;
import com.lsqt.core.dao.hibernate.impl.ParamType;

/**
 * 该SQL执行器,提供简易并直接的调用方式操作数据库.(注意:在多个service层内调用多个执行器,没有事务控制)
 * 该类提供在一些只读的场景下使用,如:例出某公司上市的产品信息等.
 * @author 袁明敏
 *
 */
@SuppressWarnings("static-access")
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
	 * 批量执行SQL更新语句
	 * @param sql 带占位符的SQL语句
	 * @param dataTable 数据表格 
	 */
	public static boolean executeSql(String sql,Object [][] dataTable) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		return run.batch(sql, dataTable).length>0;
	}
	
	/**
	 * 执行查询语句,返回二维结果集
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> executeSqlQuery(String sql) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		
		return  run.query(sql, new ArrayListHandler(){
			@Override
			protected Object[] handleRow(ResultSet rs) throws SQLException {
				return super.handleRow(rs);
			}
		});
	}
	
	/**
	 * 执行带"?"占位符的查询语句,返回二维结果集
	 * @param sql
	 * @param paramValues
	 * @return
	 * @throws SQLException
	 */
	public static List<Object[]> executeSqlQuery(String sql,Object [] paramValues) throws SQLException{
		QueryRunner run = new QueryRunner(dataSource );
		return run.query(sql,new ArrayListHandler(){
			@Override
			protected Object[] handleRow(ResultSet rs) throws SQLException {
				return super.handleRow(rs);
			}
		},paramValues);
	}
	
	// -----------------------------------------存储过程执行-----------------
	/**
	 * @param paramValues
	 *            paramValues
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
		System.out.println(holdString);
		return holdString;
	}
	
	/**
	 * 存储过程调用 (返回结果集+输出参数+执行系列更新语句),调用时显示指定入参和输出参数据数据类型
	 * 
	 * 
	 * **/
	public static DataSet excuteProcedure(final String procedureName, final Object[] paramValues, final int[] paramValueTypes) {
		if (paramValues.length != paramValueTypes.length) {
			try {
				throw new DaoException("参数值和类型指定,不匹配");
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		final DataSet DATASET = new DataSet();

		String sqlHold = processProcedureParamHold(paramValues);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con=null;
		try {
			con=dataSource.getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + sqlHold + ")}");

			List<Integer> outputParamIndex = new ArrayList<Integer>(); // 输出参数索引
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
			e.printStackTrace();

		} finally {
			DbHelper.destroy(con, cstmt, rs); // 连接的关闭让hibernate去管理
		}
		return DATASET;
	}
}
