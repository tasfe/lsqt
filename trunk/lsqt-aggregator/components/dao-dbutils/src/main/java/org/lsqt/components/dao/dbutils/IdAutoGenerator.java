package org.lsqt.components.dao.dbutils;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.lsqt.components.dto.DataTable;


public class IdAutoGenerator implements IdGenerator{
	private  SqlExecutor sqlExecutor;
	
	private IdAutoGenerator(){}
	
	public IdAutoGenerator(SqlExecutor sqlExecutor){
		this.sqlExecutor=sqlExecutor;
	}
	
	
	/**
	static {
		String strAdjust=null; //机器标识号，再优化（来自配置文件）
		if(strAdjust!=null){
			adjust = Integer.valueOf(strAdjust);
		}
		
		//初使化数据源，再优化（来自配置文件）
		Properties p=new Properties();
		p.put("driverClassName", "com.p6spy.engine.spy.P6SpyDriver"); //com.mysql.jdbc.Driver
		p.put("username","root");
		p.put("password", "123456");
		p.put("url", "jdbc:mysql://localhost:3306/oaonsite?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&autoReconnect=true&failOverReadOnly=false");
		DataSource ds;
		try {
			dataSource = BasicDataSourceFactory.createDataSource(p);
			sqlExecutor=new SqlExecutor(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	**/
	
	/**
	 * （集群部署时的应用服务器编号,一台机器默认编号为1）
	 */
	private static long adjust = 1;
	/**
	 * ID当前值
	 */
	private static long nextId = 0;
	/**
	 * 当前ID的上限
	 */
	private static long lastId = -1;
	
	
	private  void getNextIdBlock() {
		Long bound=-1L;
		Integer incremental=-1;
		String sql="select bound,incremental from sys_db_id t0 where t0.id=?";
		String upSql="update sys_db_id  set bound=? where id=?";
		try{
			DataTable dataTable=sqlExecutor.executeQuery(sql, adjust);
			Map map=dataTable.getScalarRowMap();
			bound = Long.parseLong(map.get("bound").toString());
			incremental = Integer.parseInt(map.get("incremental").toString());
			nextId = bound;
			lastId = bound + incremental;
			
			sqlExecutor.executeUpdate(upSql, lastId,adjust);
			System.out.println("get next block last id is : " +  lastId);
		}
		catch(Exception e){
			e.printStackTrace();
			insertNewComputer();
		}
	}
	
	/**
	 * 不存在该计算机编号的则插入一条记录
	 */
	private  void insertNewComputer(){
		try{
			lastId = 10000;
			String sql="insert into sys_db_id (id,incremental,bound) values ("+adjust+",10000,"+lastId+")";
			sqlExecutor.executeUpdate(sql);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 产生一个唯一ID。 使用同步，防止重复
	 * 
	 */
	public  synchronized Long getId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}
		long _nextId = nextId++;
		return _nextId + adjust*10000000000000L;
	}

}
