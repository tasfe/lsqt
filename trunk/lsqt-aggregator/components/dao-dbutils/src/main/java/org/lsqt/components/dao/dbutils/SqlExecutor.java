package org.lsqt.components.dao.dbutils;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.sql.SqlType;
import org.lsqt.components.dto.DataSet;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;




/**
 * <pre>
 * 
 * 功能说明: 
 * 	  轻量级SQL执行器.
 * 
 * 编写日期:2013-12-26
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2013-12-26
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 */
public class SqlExecutor {
	
	private DataSource dataSource;
	private QueryRunner run ;
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.run=new QueryRunner(dataSource);
	}
	
	public SqlExecutor(DataSource dataSource){
		this.dataSource = dataSource;
		this.run=new QueryRunner(dataSource);
	}
	
	
	
	public SqlExecutor(){
		/*hack code , will delete ！！！
		Properties p=new Properties();
		p.put("driverClassName", "com.p6spy.engine.spy.P6SpyDriver"); //com.mysql.jdbc.Driver
		p.put("username","root");
		p.put("password", "123456");
		p.put("url", "jdbc:mysql://localhost:3306/oaonsite?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&autoReconnect=true&failOverReadOnly=false");
		DataSource ds;
		try {
			ds = BasicDataSourceFactory.createDataSource(p);
			this.run=new QueryRunner(ds);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		*/
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
	 * 处理结果集.
	 * @param rs -
	 * @return -
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final  DataTable fillDataTable(ResultSet rs) throws SQLException{
		//处理默认表头
		int cnt=rs.getMetaData().getColumnCount();
		List<Object> dataHead=new ArrayList<Object>();
		for(int i=0;i<cnt;i++){
			dataHead.add(rs.getMetaData().getColumnLabel(i+1));
		}
		
		List<List<Object>> data=new ArrayList<List<Object>>();
		//if(rs.first()){
			while (rs.next()) {
				List row=new ArrayList();
				for (int i = 1; i <= cnt; i++) {
					row.add(rs.getObject(i));
				}
				data.add(row);
			}
		//}
		
		Object [][] dataBody=new Object[data.size()][];
		
		for(int i=0;i<dataBody.length;i++){
			System.out.println(Arrays.asList(data.get(i).toArray()));
			dataBody[i]=data.get(i).toArray();
		}
		DataTable table=new DataTable(dataHead.toArray(new String[dataHead.size()]), dataBody);
		return table;
	}
	
	//-------------------------------------------------------------------------------
	
	
	private static DataSet processProcedureResultSet(CallableStatement cstmt,Object ... params) throws SQLException{
		DataSet ds=new DataSet();
	
		ResultSet rs = null;
		
		for(int i=0;i<params.length;i++){
			cstmt.setObject(i+1,params[i]);
		}
		
		boolean hasResults = cstmt.execute();
		while (hasResults) {
			rs = cstmt.getResultSet();
			DataTable table=fillDataTable(rs);
			ds.add(table);
			hasResults = cstmt.getMoreResults();
		}
		return ds;
	}
	
	/**
	 * 执行存储过程,返回结果集
	 * @param procedureName 存储过程名称
	 * @return DataSet 
	 */
	public DataSet executeProcedure(String procedureName) {
		
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = this.dataSource.getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "()}");

			 return processProcedureResultSet(cstmt);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}

	}
	
	/**
	 * 执行只带输入参数的存储过程
	 * @param procedureName  存储过程名称
	 * @param paramValues 入参值
	 * @return DataSet 数据集
	 */
	public DataSet executeProcedure(String procedureName,Object ... params) {
		
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = this.dataSource.getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + processProcedureParamHold(params) + ")}");
			
			return processProcedureResultSet(cstmt,params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}
	}
	
	/**
	 * 存储过程调用 (返回结果集+输出参数+执行系列更新语句),调用时显示指定入参和输出参数据数据类型
	 * 
	 * @param procedureName 存储过程名称
	 * @param paramValues 入参值和输出参数的值
	 * @param paramValueTypes 储存过程入参和输出参数据的数据类型:SqlType.String待
	 * @return DataSet 数据集
	 * **/
	public DataSet executeProcedure(String procedureName, Object[] paramValues, SqlType[] paramValueTypes) {
		if (paramValues.length != paramValueTypes.length) {
			//LOGGER.error("procedure execute fail, Parameters and parameter types value , the length not equal  ");
			return null;
		}
		
		DataSet dataSet= new DataSet();

		String sqlHold = processProcedureParamHold(paramValues);
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Connection con=null;
		try {
			
			con=this.dataSource.getConnection();
			cstmt = con.prepareCall("{call " + procedureName + "(" + sqlHold + ")}");

			List<Integer> outputParamIndex = new ArrayList<Integer>(); 
			for (int i = 0; i < paramValues.length; i++) {
				if (paramValues[i] == SqlType.INOROUT) {
					cstmt.registerOutParameter(i + 1, paramValueTypes[i].getTypeValue());  // 注册输出参数
					outputParamIndex.add(i + 1);
				} else {
					cstmt.setObject(i + 1, paramValues[i]);
				}
			}

			boolean hasResults = cstmt.execute();

			// 处理存储过程输出参数值
			List<Object> outputValues = new ArrayList<Object>();
			for (int i = 0; i < outputParamIndex.size(); i++) {
				outputValues.add(cstmt.getObject(outputParamIndex.get(i)));
			}

			dataSet.setOutputParams(outputValues.toArray(new Object[outputValues.size()]));

			while (hasResults) {
				rs = cstmt.getResultSet();
				DataTable table=fillDataTable(rs);
				dataSet.add(table);
				hasResults = cstmt.getMoreResults();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DbUtils.closeQuietly(con, cstmt, rs);
		}
		return dataSet;
	}
	
	
	
	/**
	 * 执行更新的SQL语句
	 * @param sql
	 * @return
	 */
	public  int executeUpdate(String sql){
		try{
			return run.update(sql);
		}catch(SQLException ex){
			
			ex.printStackTrace();
			return 0;
		}
	}
	
	public int executeUpdate(String sql,Object... paramValues){
		try{
			return run.update(sql,paramValues);
		}catch(SQLException ex){
			
			ex.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 批量执行SQL更新语句
	 * @param sql 带占位符的SQL语句
	 * @param dataTable 参数数据表格 
	 
	public int[] executeUpdate(String sql, Object[][] dataTable) {
		
		try {
			return run.batch(sql, dataTable);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	*/


	/**
	 * 执行查询语句,返回二维结果集
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public  DataTable executeQuery(String sql){
		
	
		try {
			return  run.query(sql, new ResultSetHandler<DataTable>(){
				 @Override
				public DataTable handle(ResultSet rs) throws SQLException {
					return fillDataTable(rs);
				}
			});
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		} 
	}

	public Object executeScalar(String sql,Object... params){
		try{
			return run.query(sql, new ResultSetHandler<Object>(){
				@Override
				public Object handle(ResultSet rs) throws SQLException {
					if(rs.first()){
						return rs.getObject(1);
					}
					return null;
				}
			},params);
		}catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public Object executeScalar(String sql){
		return executeScalar(sql,new Object[]{});
	}
	/**
	 * 执行带"?"占位符的查询语句,返回二维结果集
	 * @param sql
	 * @param paramValues
	 * @return
	 * @throws SQLException
	 */
	public  DataTable executeQuery(String sql,Object ... param){
		try{
		/**
		Connection con=null;
		ResultSet rs=null;
		Statement pstmt=null;
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con=	DriverManager.getConnection("jdbc:mysql://localhost:3306/oaonsite", "root", "123456");
			//con=this.dataSource.getConnection();
			
			System.out.println(sql+"===>"+Arrays.asList(param));
			
			pstmt= con.createStatement();
			
			rs= pstmt.executeQuery("SELECT * FROM oaonsite.sys_dic where sn= '0' and itemName like '%普%' ");
		
			//pstmt.setString(2, "%"+"普"+"%");
			for(int i=0;i<param.length;i++){
				pstmt.setObject(i+1, param[i]);
			}
		//	rs= pstmt.executeQuery(sql);
			DataTable table= fillDataTable(rs);
			
			return table;
			 **/
			
			System.out.println(sql+"===>"+Arrays.asList(param));
			return run.query(sql, new ResultSetHandler<DataTable>(){
				@Override
				public DataTable handle(ResultSet rs) throws SQLException {
					return fillDataTable(rs);
					
				}
			},param);
			
			/**  **/
			
			
		}catch(SQLException ex){
			ex.printStackTrace();
			return null;
		}finally{
			//DbUtils.closeQuietly(con, pstmt, rs);
		}
	}
	
	
	/**
	 * 执行分页查询.暂只支持MYSQL.
	 * @param page
	 * @param sql
	 */
	public void executeQueryPage(final Page page, String sql) {
		executeQueryPage(page, sql, new Object[] {});
	}
	
	/**
	 * 执行分页查询.暂只支持MYSQL.
	 * @param page
	 * @param sql
	 * @param param
	 */
	public void executeQueryPage(final Page page,String sql,Object... param){
		int p=page.getCurrentPage()-1;
		int n=page.getPageSize();
		
		
		Integer cnt=0;
		String countSQL="select count(*) from ( "+sql+" )  amount ";
		
		Object amount=executeScalar(countSQL,param);
		if(amount != null){
			cnt=Integer.valueOf(amount.toString());
		}

		int totalPage = Double.valueOf(Math.ceil(Double.valueOf(cnt) / n)).intValue();
		if ((p + 1) * n > cnt) {
			if (p >= totalPage) {
				p = totalPage - 1;
			}
			if (p < 0) {
				p = 0;
			}
		}
		
		StringBuffer pageSQL = new StringBuffer(" select * from ( " + sql + ")  t0   limit " + (p * n) + " , " + n + "  ");
		System.out.println(pageSQL+"  ====>参数值:"+Arrays.asList(param));
		
		DataTable dataTable = executeQuery(pageSQL.toString(), param);

		BeanUtil.forceSetProperty(page, "dataTable", dataTable);
		BeanUtil.forceSetProperty(page, "totalRecord", cnt);
		BeanUtil.forceSetProperty(page, "totalPage", totalPage);
		BeanUtil.forceSetProperty(page, "currentPage", p + 1);
		BeanUtil.forceSetProperty(page, "hasNext", ((p + 1) < totalPage) ? true:false);
		BeanUtil.forceSetProperty(page, "hasPreviou", ((p - 1) < 0) ? false: true);
		
	}

}


