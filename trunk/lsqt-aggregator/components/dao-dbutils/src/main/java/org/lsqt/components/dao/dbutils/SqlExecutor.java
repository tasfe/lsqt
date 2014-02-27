package org.lsqt.components.dao.dbutils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.sql.SqlType;
import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;
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
 * 修改日期：2014-02-26
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 */
public class SqlExecutor {
	private SqlTranslator sqlTranslator;
	private DataSource dataSource;
	private QueryRunner run ;

	
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
	
	public void SqlExecutor(DataSource dataSource){
		this.dataSource=dataSource;
		this.run=new QueryRunner(dataSource);
		this.sqlTranslator=new SqlTranslator(this);
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.run=new QueryRunner(dataSource);
		this.sqlTranslator=new SqlTranslator(this);
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
			
			System.out.println(sql+"===>"+Arrays.asList(param));
			return run.query(sql, new ResultSetHandler<DataTable>(){
				@Override
				public DataTable handle(ResultSet rs) throws SQLException {
					return fillDataTable(rs);
					
				}
			},param);
			
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

	public int entitySaveOrUpdate(Object entity){ //主键值目前只采用自增长策略，以后自定义的策略再优化吧
		try {
			return sqlTranslator.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

	public int entityDelete(Object entity){ 
		try {
			return sqlTranslator.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//SQL翻译器实现，纯pojo的CRUD操作，须标注实体注解信息
	@SuppressWarnings("unused")
	private class SqlTranslator{
		private SqlExecutor sqlExecutor;
		private SqlTranslator(){}
		private SqlTranslator(SqlExecutor sqlExecutor){
			this.sqlExecutor=sqlExecutor;
		}
		
		private StringBuffer getDeleteSQL(Map map){
			StringBuffer sql=new StringBuffer();
			if(map.get("table")!=null && map.get("id")!=null){
				sql.append("delete from " + map.get("table") +" where "+map.get("idColumn")+" = ? ");
			}
			System.out.println(sql);
			return sql;
		}
		
		private StringBuffer getUpdateSQL(Map map){
			StringBuffer sql=new StringBuffer();
			if(map.get("table")!=null && map.get("colums")!=null){
				String table=map.get("table").toString();
				List<String> columns=(List<String>)map.get("colums");
				
				sql.append(" update "+table+" set ");
				for(int i=0;i<columns.size();i++){
					sql.append(columns.get(i)+"= ? ");
					if(i!=columns.size()-1){
						sql.append(" , ");
					}
				}
				sql.append(" where "+map.get("idColumn")+" = ? ");
			}
			System.out.println(sql);
			return sql;
		}
		
		private StringBuffer getInsertSQL(Map map){
			StringBuffer sql=new StringBuffer();
			
			if(map.get("table")!=null && map.get("colums")!=null){
				String table=map.get("table").toString();
				List<String> columns=(List<String>)map.get("colums");
				
				//组装SQL
				sql.append(" insert into "+table+" ( ");
				
				for(int i=0;i<columns.size();i++){
					sql.append(columns.get(i));
					if(i!=columns.size()-1){
						sql.append(" , ");
					}
				}
				if(map.get("idColumn")!=null){
					sql.append(", "+map.get("idColumn")+" ");
				}
				
				sql.append(" ) values ( ");
				
				List params=(List)map.get("params");
				for(int i=0;i<params.size();i++){
					sql.append(" ? ");
					if(i!=params.size()-1){
						sql.append(" , ");
					}
				}
				
				if(map.get("idColumn")!=null){
					sql.append(", ? ");
				}
				sql.append(" ) ");
				System.out.println(sql+"==>"+params);
			}
			return sql;
		}
		
		private  StringBuffer getParamSQL(Object ... param){
			StringBuffer sql=new StringBuffer();
			for(int i=0;i<param.length;i++){
				sql.append(" ? ");
				if(i!=param.length-1){
					sql.append(" , ");
				}
			}
			System.out.println(sql);
			return sql;
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private  Map processAnnotations(Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			Map info=new LinkedHashMap();
			
			Class clazz=entity.getClass();
			Table table=(Table)clazz.getAnnotation(Table.class);
			if(table!=null){
				
				 //1.获取schema
				String schema=table.schema();
				String tableName=table.name();
				info.put("table", schema.concat(".").concat(tableName));
				
				
				
				List<String> sqlColoumns=new ArrayList<String>();
				List params=new ArrayList();
				Map<String,String> getter=getSetterGetterMap(clazz,true); //字段对应的setter方法
				
				for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
					for(Field e:superClass.getDeclaredFields()){
						
						//获取主键
						Id id=e.getAnnotation(Id.class);
						if(id!=null){
							info.put("idColumn", StringUtil.isEmpty(id.name()) ? "id":id.name());
							info.put("idProperty", e.getName());
							
							for(Method m: superClass.getDeclaredMethods()){
								if(("get"+e.getName()).equalsIgnoreCase(m.getName())){
									info.put("idValue",m.invoke(entity, null));
									break;
								}
							}
							
						}
						
						//2.获取字段
						Column columns=e.getAnnotation(Column.class);
						if(columns!=null){
							sqlColoumns.add(columns.name());
						}
						
						//3.获取字段值
						for(Method m: superClass.getDeclaredMethods()){
							if(getter.get(e.getName())!=null && getter.get(e.getName()).toString().equals(m.getName())){
								Object p=m.invoke(entity, null);
								params.add(p);
							}
							
						}
						
					}
				}
				info.put("colums", sqlColoumns);
				
				info.put("params", params);
				
			}
			return info;
		}
	 
		@SuppressWarnings("rawtypes")
		private  Map<String,String> getSetterGetterMap(Class clazz,boolean isGetter){
			
			Set<String> fieldSet=new LinkedHashSet<String>();
			Set<String> boolFieldSet=new LinkedHashSet<String>();
			
			Set<String> methodSet=new LinkedHashSet<String>();
			Set<String> boolmethodSet=new LinkedHashSet<String>();
			for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					//不取id注解标志的字段
					if(e.getAnnotation(Id.class)!=null){
						continue;
					}
					
					if(e.getGenericType().equals(boolean.class)){
						boolFieldSet.add(e.getName());
					}else{
						fieldSet.add(e.getName());
					}
				}
				
				for(Method m: superClass.getDeclaredMethods()){ 
					if(isGetter){
						
						if(m.getParameterTypes()!=null && m.getParameterTypes().length==0 &&
								(!m.getReturnType().equals(void.class)) ){//判断getter 方法：无入参，有返回值
							
							if ( m.getReturnType().equals(boolean.class)) { 
								boolmethodSet.add(m.getName());
							}else{
								methodSet.add(m.getName());
							}
						}
					}else{
					
						if(m.getReturnType().equals(void.class) && m.getParameterTypes().length ==1){//判断setter 方法：只有一个入参，无返回值
							if ( !(m.getParameterTypes()[0].equals(boolean.class))) { 
								methodSet.add(m.getName());
							}else{
								boolmethodSet.add(m.getName());
							}
						}
					}
					
				}
			}
			/*
			 System.out.println("aaaaa==="+fieldSet);
			System.out.println("bbbbb==="+boolFieldSet);
			
			 System.out.println("ccccc==="+methodSet);
			System.out.println("ddddd==="+boolmethodSet);
			*/
			
			//找到每个字段的setter方法.  (注：isXXX开头的boolean字段,其setter方法为 setXXX)
			Map<String,String> map=new LinkedHashMap<String,String>();
			if(isGetter==false){
				for(String f:fieldSet){
					for(String m:methodSet){
						if(("set"+f).equalsIgnoreCase(m)){
							map.put(f, m);
							break;
						}
					}
				}
				
				//isBad ==> setBad , msTool ==> setMsTool
				for(String f:boolFieldSet){
					for(String m:boolmethodSet){
						String setM="set"+f.substring(2, f.length()); //针对isXXX属性
						if(setM.equalsIgnoreCase(m)){
							map.put(f, m);
							break;
						}
						
						if(("set"+f).equalsIgnoreCase(m)){ 	// 针对非isXXX属性
							map.put(f, m);
							break;
						}
						
					}
				}
				System.out.println(clazz.getName()+"'s  setter are, size is "+map.keySet().size()+" :  "+map);
			}else{
				
				//isBad ==> isBad , msTool ==> isMsTool , hasTool ==> isHasTool
				for(String f:fieldSet){
					for(String m:methodSet){
						if(("get"+f).equalsIgnoreCase(m)){
							map.put(f, m);
							break;
						}
					}
				}
				
				for(String f:boolFieldSet){
					for(String m:boolmethodSet){
						if(f.equalsIgnoreCase(m)){
							map.put(f, m);
							break;
						}
						
						if(f.equalsIgnoreCase(m.substring(2,m.length()))){
							map.put(f, m);
							break;
						}
					}
				}
				System.out.println(clazz.getName()+"'s  getter are, size is "+map.keySet().size()+" :  "+map);
			}
			return map;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int saveOrUpdate(Object entity) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
				//id无值，则进行插入操，否则更新实体
				
				Map map = processAnnotations(entity);
				if (map.get("idColumn") != null && map.get("idValue") == null) {
					List temp = (List) map.get("params");
					
					StringBuffer sql = getInsertSQL(map); // sql使终显示插入id字段，这个应该有注解的id生成策略来决定 ,再优化
					
					Object pk=IdAutoGenerator.getId();
					temp.add(pk);
					
					sqlExecutor.executeUpdate(sql.toString(), temp.toArray());
					BeanUtil.forceSetProperty(entity, map.get("idColumn").toString(), pk);
					
				} else if (map.get("idColumn") != null && map.get("idValue") != null) {
					List temp = (List) map.get("params");
					temp.add(map.get("idValue"));
					
					StringBuffer sql= getUpdateSQL(map);
					
					return sqlExecutor.executeUpdate(sql.toString(),temp.toArray());
				}
				return 0;
		}
		
		public int delete(Object ... entitys) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			int cntEffect=0;
			for(Object entity:entitys){  //需用batch操作，再优化
				Map map = processAnnotations(entity);
				String idColum=(String)map.get("id");
				Object idParam=map.get("idValue");
				StringBuffer sql=getDeleteSQL(map);
				cntEffect+=sqlExecutor.executeUpdate(sql.toString(), idParam);
			}
			return cntEffect;
		}
		
	}
	
}


