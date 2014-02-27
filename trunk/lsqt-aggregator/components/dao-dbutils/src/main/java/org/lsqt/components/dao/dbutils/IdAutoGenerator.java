package org.lsqt.components.dao.dbutils;

import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.lsqt.components.dto.DataTable;


public class IdAutoGenerator implements IdGenerator{
	private static DataSource dataSource;
	private static SqlExecutor sqlExecutor;
	
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
			sqlExecutor=new SqlExecutor();
			sqlExecutor.setDataSource(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
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
	
	
	@Override
	public Object getId() {
		return genId();
	}
	
	private static void getNextIdBlock() {
		
		Long bound=-1L;
		Integer incremental=-1;
		String sql="SELECT bound,incremental FROM SYS_DB_ID T WHERE T.ID=?";
		String upSql="UPDATE SYS_DB_ID  SET BOUND=? WHERE ID=?";
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
	private static void insertNewComputer(){
		try{
			lastId = 10000;
			String sql="INSERT INTO SYS_DB_ID (id,incremental,bound) VALUES("+adjust+",10000,"+lastId+")";
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
	public static synchronized long genId() {
		if (lastId <= nextId) {
			getNextIdBlock();
		}
		long _nextId = nextId++;
		return _nextId + adjust*10000000000000L;
	}
	
	public static void main(String ...strins){
		System.out.println(IdAutoGenerator.genId());
	}
}
