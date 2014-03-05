package org.lsqt.components.dao.dbutils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.lang.ArrayUtil;
import org.lsqt.components.util.lang.MapUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.components.util.sql.SqlType;
import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;
import org.lsqt.components.dto.DataSet;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;

import static org.lsqt.components.dao.dbutils.EntityAnnotationUtil.*;



/**
 * <pre>
 * 
 * 功能说明: 
 * 	  轻量级SQL执行器.
 * 
 * 编写日期:2013-12-26
 * 作者:Sky
 * 
 * 历史记录
 * 修改日期：2014-02-26
 * 修改人: Sky
 * 修改内容：添加注解元信息解析
 * </pre>
 */
public class SqlExecutor {
	private SqlTranslator sqlTranslator;
	private DataSource dataSource;
	private QueryRunner run ;

	
	private SqlExecutor(){ //该类实例化必须使用数据源
	}
	
	public SqlExecutor(DataSource dataSource){
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
	private static String processParamHolder(Object[] paramValues) {
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
			cstmt = con.prepareCall("{call " + procedureName + "(" + processParamHolder(params) + ")}");
			
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

		String sqlHold = processParamHolder(paramValues);
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
		//System.out.println(pageSQL+"  ====>参数值:"+Arrays.asList(param));
		
		DataTable dataTable = executeQuery(pageSQL.toString(), param);

		BeanUtil.forceSetProperty(page, "dataTable", dataTable);
		BeanUtil.forceSetProperty(page, "totalRecord", cnt);
		BeanUtil.forceSetProperty(page, "totalPage", totalPage);
		BeanUtil.forceSetProperty(page, "currentPage", p + 1);
		BeanUtil.forceSetProperty(page, "hasNext", ((p + 1) < totalPage) ? true:false);
		BeanUtil.forceSetProperty(page, "hasPreviou", ((p - 1) < 0) ? false: true);
		
	}

	/**
	 * 更新实体所有属性数据
	 * @param entity
	 * @return
	 */
	public int entitySaveOrUpdate(Object entity){ //主键值目前只采用自增长策略，以后自定义的策略再优化吧
		try {
			return sqlTranslator.saveOrUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}

	/**
	 * 只更新或插入指定的属性数据
	 * @param entity
	 * @param entityProperties
	 * @return
	 */
	public int entitySaveOrUpdate(Object entity,String ... entityProperties){
		try {
			return sqlTranslator.saveOrUpdate(entity,entityProperties);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 删除实体，实体需有id值
	 * @param entity
	 * @return
	 */
	public int entityDelete(Object entity){ 
		try {
			return sqlTranslator.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	public <T> T  entityGetById(Class<T> entityClazz,Object id ){
		try {
			return sqlTranslator.entityGetById(entityClazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} 
	}
	
	public int entityDeleteByIds(Class entityClazz,Object ...ids){ 
		return sqlTranslator.deleteByIds(entityClazz, ids);
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
		
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public int saveOrUpdate(Object entity,String ...props) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
				Object idValue=getEntityIdValue(entity);
				
				if (idValue!=null) { //id无值，则进行插入操，否则更新实体
					String schema=getDbSchema(entity.getClass());
					String table= getDbTable(entity.getClass());
					table=StringUtil.isEmpty(schema) ? table:schema.concat(".").concat(table);
					
					List<String>  coloumnList=new ArrayList<String>();
					List  paramValues=new ArrayList();
					
					Map<String,Object> cvMap=getEntityColumnValueMap(entity);
					for(String k: cvMap.keySet()){
						coloumnList.add(k);
						paramValues.add(cvMap.get(k));
					}
					if(props.length>0){ 
						List<String> effectColumn=getDbColumnByProperty(entity.getClass(), props);//更新指定的列
						List<Object> effectParamValues=new ArrayList<Object>();
						
						for(int i=0;i<coloumnList.size();i++){
							if(effectColumn.contains(coloumnList.get(i))){
								effectParamValues.add(paramValues.get(i));
							}
						}
						
						String sql = getInsertSQL(table, effectColumn,effectParamValues);
						
						System.out.println("stmt:"+sql+" | param:"+effectParamValues);
						return sqlExecutor.executeUpdate(sql, effectParamValues.toArray());
					}else{
						String sql = getInsertSQL(table, coloumnList,paramValues);
						
						System.out.println("stmt:"+sql+" | param:"+paramValues);
						return sqlExecutor.executeUpdate(sql, paramValues.toArray());
					}
				} else  {  //更新全部列
					String table=getDbTable(entity.getClass()); 
					
					String pkColumn=getDbIdColumn(entity.getClass());
					Object pkValue=getEntityIdValue(entity);
					
					List<String> columns=getDbColumns(entity.getClass());
					
					final Map<String,Object> cpMap=getEntityColumnValueMap(entity);
					
					return sqlExecutor.executeUpdate(getUpdateSQL(pkColumn,table,cpMap), MapUtil.toValueList(cpMap).add(pkValue));
				}
		}
		
		/**
		 * 跟据id更新实体SQL
		 * @param pkColumn
		 * @param table
		 * @param cpMap
		 * @return
		 */
		@SuppressWarnings("unchecked")
		private String getUpdateSQL(String pkColumn,String table,final Map<String,Object> cpMap){
			StringBuffer sql=new StringBuffer();
			sql.append(" update "+table+" set ");
			
			List<String> temp=MapUtil.toKeyList(cpMap);
			for(int i=0;i<temp.size();i++){
				sql.append(temp.get(i)+"=? ");
				if(i!=temp.size()-1){
					sql.append(", ");
				}
			}
			sql.append(" where "+pkColumn+" = ? ");
			System.out.println(sql);
			return sql.toString();
		}

		
		private String getInsertSQL(String table, List<String> effectColumn,List<Object> effectParamValues) {
			String sql="insert into ".concat(table)
					.concat(" (")
					.concat(ArrayUtil.join(effectColumn, ","))
					.concat(" ) values (")
					.concat(processParamHolder(effectParamValues.toArray()))
					.concat(" )");
			return sql;
		}
		

		public int delete(Object ... entitys) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			int cntEffect=0;
			for(Object entity:entitys){  //需用batch操作，再优化
				deleteByIds(entity.getClass(), getEntityIdValue(entity));
			}
			return cntEffect;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public int deleteByIds(Class entityClazz,Object ...ids){
			
			String idColumn=getDbIdColumn(entityClazz);
			
			
			//生成删除语句
			Table table=(Table)entityClazz.getAnnotation(Table.class);
			if(table!=null){
				
				StringBuffer sql=new StringBuffer();
				String schema=getDbSchema(entityClazz);
				sql.append("delete from "
						.concat(schema == null ? "" : schema.concat("."))
						.concat(getDbTable(entityClazz)).concat(" where ")
						.concat(idColumn).concat(" in (")
						.concat(processParamHolder(ids).concat(" ) ")));
				
				System.out.println(sql+"==>"+Arrays.asList( ids));
				return sqlExecutor.executeUpdate(sql.toString(), ids);
			}
			return 0;
		}
		
		public <T> T entityGetById(Class<T> entityClazz,Object id) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			//生成查询语句
			Table table=(Table)entityClazz.getAnnotation(Table.class);
			if(table!=null){
				
				List<String> sqlColoumns=getDbColumns(entityClazz);
				
				StringBuffer sql=new StringBuffer();
				sql.append(" select "+ArrayUtil.join(sqlColoumns, ","));
				sql.append(" from ");
				sql.append(getDbSchema(entityClazz)==null ? "": getDbSchema(entityClazz).concat("."));
				sql.append(getDbTable(entityClazz));
				sql.append(" where "+getDbIdColumn(entityClazz)+" =  ?");
				
				System.out.println(sql+"==>"+Arrays.asList(id));
				
				
				//3.跟据注解信息还原bean对象
				DataTable dataTable= sqlExecutor.executeQuery(sql.toString(), id);
				Map<String,Object> map=dataTable.getScalarRowMap();  //数据库结果集字段与值
				
				return toEntity(map, entityClazz);
			}
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//处理主对象的所有子对象
		/**
		@SuppressWarnings("rawtypes")
		private void processCascadeSub(Object mainEntity) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			Map<String,Object> mainInfo=new LinkedHashMap<String,Object>();
			List<String> sqlColoumns=new ArrayList<String>();
			
			Class entityClazz=mainEntity.getClass();
			Map<Field,Method> getterMap=BeanUtil.getSetterGetterMap(entityClazz, true);
			
			for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					//1.获取主对象主键
					Id pk=e.getAnnotation(Id.class);
					if(pk!=null){
						mainInfo.put("idColumn", StringUtil.isEmpty(pk.name()) ? "id":pk.name());
						mainInfo.put("idValue", getterMap.get(e).invoke(mainEntity, null));
						break;
					}
				}
			}
			
			
			for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					
					Sub sub=e.getAnnotation(Sub.class);
					if(sub!=null && sub.lazy()==false){
						
						//2.获取主对象关联的子对象容器
						Collection collection=null;
						Object collectionObj = getterMap.get(e).invoke(mainEntity, null);
						if(collectionObj!=null && collectionObj instanceof Collection){
							collection = (Collection)collectionObj;
						}
						
						ParameterizedType type=(ParameterizedType)e.getGenericType();
						if(type.getActualTypeArguments()!=null && type.getActualTypeArguments().length>0){
							Type temp=type.getActualTypeArguments()[0];
							Class eClazz = (Class) temp; //子对象类型
							//3.跟据主对象的id值，加载子对象
							collection.add(entityGetById(eClazz,mainInfo.get("idValue")));
						}
					}
				}
			}
		}
		**/
	
	}
	
}


