package com.lsqt.components.dao.dbutils;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.sql.SqlType;

import com.lsqt.components.dto.DataRow;
import com.lsqt.components.dto.DataSet;
import com.lsqt.components.dto.DataTable;
import com.lsqt.components.dto.Page;



/**
 * 
 * @author Sky
 *
 */
public class SqlExecutor {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
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
		return holdString;
	}
	
	/**
	 * 执行存储过程,返回结果集
	 * @param procedureName 存储过程名称
	 * @return DataSet 
	 */
	public DataSet executeProcedure(final String procedureName) {
		final DataSet DATASET = new DataSet();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = this.dataSource.getConnection();
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
			
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}

		return DATASET;
	}
	
	/**
	 * 执行只带输入参数的存储过程
	 * @param procedureName  存储过程名称
	 * @param paramValues 入参值
	 * @return DataSet 数据集
	 */
	public DataSet executeProcedure(final String procedureName, final Object[] paramValues) {
		final DataSet DATASET = new DataSet();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = this.dataSource.getConnection();
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
			
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}
			
		return DATASET;
	}
	
	/**
	 * 存储过程调用 (返回结果集+输出参数+执行系列更新语句),调用时显示指定入参和输出参数据数据类型
	 * 
	 * @param procedureName 存储过程名称
	 * @param paramValues 入参值和输出参数的值
	 * @param paramValueTypes 储存过程入参和输出参数据的数据类型:SqlType.String待
	 * @return DataSet 数据集
	 * **/
	public DataSet executeProcedure(final String procedureName, final Object[] paramValues, final SqlType[] paramValueTypes) {
		if (paramValues.length != paramValueTypes.length) {
			//LOGGER.error("procedure execute fail, Parameters and parameter types value , the length not equal  ");
			return null;
		}

		final DataSet DATASET = new DataSet();

		String sqlHold = processProcedureParamHold(paramValues);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con=null;
		try {
			
			con=this.dataSource.getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + sqlHold + ")}");

			List<Integer> outputParamIndex = new ArrayList<Integer>(); 
			for (int i = 0; i < paramValues.length; i++) {
				if (paramValues[i] == SqlType.HOLDER) {
					cstmt.registerOutParameter(i + 1, paramValueTypes[i].getTypeValue());
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
			//LOGGER.error("execute procedure fail ==> "+e.getMessage());
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}
		return DATASET;
	}
	
	
	
	/**
	 * 执行更新的SQL语句
	 * @param sql
	 * @return
	 */
	public  boolean executeSql(String sql){
		QueryRunner run = new QueryRunner(dataSource);
	
		try{
			return run.update(sql)>0;
		}catch(SQLException ex){
			//LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return false;
		}
	}
	
	public boolean executeSql(String sql,Object[] paramValues){
		QueryRunner run = new QueryRunner(this.dataSource);
		try{
			return run.update(sql,paramValues)>0;
		}catch(SQLException ex){
			//LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return false;
		}
	}
	
	/**
	 * 批量执行SQL更新语句
	 * @param sql 带占位符的SQL语句
	 * @param dataTable 参数数据表格 
	 */
	public boolean executeSql(String sql,Object [][] dataTable){
		QueryRunner run = new QueryRunner(this.dataSource);
		try{
			
		  return run.batch(sql, dataTable).length>0;
		  
		} catch(SQLException ex){
			//LOGGER.debug("execute sql fail ==> "+ex.getMessage());
			return false;
		}
	}

	/**
	 * 执行查询语句,返回二维结果集
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public  List<Object[]> executeSqlQuery(String sql){
		QueryRunner run = new QueryRunner(this.dataSource);
	
		try {
			return  run.query(sql, new ArrayListHandler(){
				@Override
				protected Object[] handleRow(ResultSet rs) throws SQLException {
					return super.handleRow(rs);
				}
			});
		} catch (SQLException ex) {
			//LOGGER.error("execute sql fail ==> " + ex.getMessage());
			return null;
		} 
	}

	/**
	 * 执行带"?"占位符的查询语句,返回二维结果集
	 * @param sql
	 * @param paramValues
	 * @return
	 * @throws SQLException
	 */
	public  List<Object[]> executeSqlQuery(String sql,Object [] paramValues){
		QueryRunner run = new QueryRunner(dataSource);
		try{
			return	run.query(sql,new ArrayListHandler(),paramValues);
		}catch(SQLException ex){
			//LOGGER.error("execute sql fail ==> "+ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 执行分页查询.暂只支持MYSQL.
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	public Page executeSqlPage(String sql,Object[] paramValues,Page page){
		int p=page.getCurrPageNum()-1;
		int n=page.getPerPageRecord();
		
		
		QueryRunner run = new QueryRunner(dataSource);
		
		Integer cnt=0;
		String countSQL=" select count(*) from ( "+sql+" )  BB ";
		
		ArrayList paramList=new ArrayList();
		for(Object t:paramValues){
			if(t!=null){
				paramList.add(t);
			}
		}
		List<Object[] > list=executeSqlQuery(countSQL,paramList.toArray());
		if(list!=null && list.size()>0){
			Object[] row=list.get(0);
			if(row!=null && row.length>0){
				cnt=Integer.valueOf(row[0].toString());
			}
		}
		
		int totalPage=Double.valueOf(Math.ceil( Double.valueOf(cnt)/n)).intValue();
		if((p+1)*n>cnt){
			if(p>=totalPage){
				p=totalPage-1;
			}
			if(p<0){
				p=0;
			}
		}
		
		
		String pageSQL=" select * from ( "+sql+") as AA ";
		pageSQL=pageSQL+"  limit "+(p*n)+" , "+n+"   ";
		List<Object[]> data=executeSqlQuery(pageSQL,paramList.toArray());
		
		 BeanUtil.forceSetProperty(page, "data", data);
		 BeanUtil.forceSetProperty(page, "totalRecord", cnt);
		 BeanUtil.forceSetProperty(page, "totalPage", totalPage);
		 BeanUtil.forceSetProperty(page, "currPageNum", p+1);
		 
		
		return page;
	}
	
	/**
	public static void main(String args[]){
		System.out.println(Math.ceil(1.2D));
		for(int i=0;i<5000;i++){
			SimpleSqlExecutor.executeSql("insert t_test_client values('"+i+"','%Y-%m-%d'),'dddd','dddd','dddd','ddddd')");
		}
	}
	**/
}


