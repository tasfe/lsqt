package org.lsqt.components.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Before;
import org.junit.Test;
import org.lsqt.components.dao.dbutils.SqlExecutor;
import org.lsqt.components.dto.DataSet;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.dto.Page;


public class SqlExecutorTest {
	private SqlExecutor sqlExecutor;
	
	@Before
	public void before(){
		
		/**
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("123456");
		ds.setUrl("jdbc:mysql://localhost:3306/oaonsite");
		**/
		
		Properties p=new Properties();
		p.put("driverClassName", "com.p6spy.engine.spy.P6SpyDriver"); //com.mysql.jdbc.Driver
		p.put("username","root");
		p.put("password", "123456");
		p.put("url", "jdbc:mysql://localhost:3306/oaonsite?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&autoReconnect=true&failOverReadOnly=false");
		DataSource ds;
		try {
			ds = BasicDataSourceFactory.createDataSource(p);
			/**
			Connection con1=ds.getConnection();
			Connection con2=ds.getConnection();
			Connection con3=ds.getConnection();
			Connection con4=ds.getConnection();
			Connection con5=ds.getConnection();
			
			
			System.out.println(con1);
			System.out.println(con2);
			System.out.println(con3);
			System.out.println(con4);
			System.out.println(con5);
			
			System.out.println("A:"+(con1==con2));
			System.out.println("B:"+(con2==con3));
			System.out.println("C:"+(con3==con4));
			System.out.println("D:"+(con4==con5));
			**/
			sqlExecutor=new SqlExecutor(ds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.hsqldb.jdbcDriver");
		ds.setUsername("sa");
		ds.setPassword("");
		ds.setUrl("jdbc:hsqldb:mem:mydatabase");
		**/
		
	}
	
	@Test
	public void executeProcedureTest(){
		DataSet ds=sqlExecutor.executeProcedure("test_p1");
		System.out.println(ds.getDataTables().size());
		 
	}
	
	@Test
	public void executeQueryTest(){
		DataTable tb=sqlExecutor.executeQuery("select * from sys_user where fullname like '%张%' limit 5");
	}
	
	@Test
	public void executeQueryTest2(){
		DataTable tb=sqlExecutor.executeQuery("SELECT * FROM oaonsite.sys_dic where sn= ? and itemName like ? ",0,"%普%");
	}
	
	@Test
	public void executeQueryPage1(){
		Page page=new Page(2,2);
		this.sqlExecutor.executeQueryPage(page, "select * from sys_user where fullname like '%张%' ");
	}
	
	@Test
	public void executeQueryPage2(){
		Page page=new Page(2,2);
		this.sqlExecutor.executeQueryPage(page, "select * from sys_user where fullname like ?","%张%");
	}
	
	@Test
	public void entitySaveOrUpdateTest(){
		for(int i=0;i<10;i++){
			SysDataSource ds=new SysDataSource();
			ds.setAlias("setAlias");
			ds.setDbType("setDbType");
			ds.setDriverName("setDriverName");
			ds.setName("setName");
			ds.setPassword("setPassword");
			ds.setUrl("url");
			ds.setUserName("setUserName");
			this.sqlExecutor.entitySaveOrUpdate(ds);
			
			ds.setAlias("XXXXXX");
			ds.setDbType("oracle");
			this.sqlExecutor.entitySaveOrUpdate(ds,"alias","dbType");
		}
	}
	
	@Test
	public void entityDeleteTest(){
		SysDataSource ds=new SysDataSource();
		ds.setId(10000025030005L);
		this.sqlExecutor.entityDelete(ds);
	}
	
	@Test
	public void entityDeleteByIdsTest(){
		int cnt=this.sqlExecutor.entityDeleteByIds(SysDataSource.class, 10000024820004L,10000024830004L,10000024830005L,10000024830006L,10000024830007L,10000024830008L);
		System.out.println("==》影响的行数："+cnt);
	}
	
	@Test
	public void entityGetByIdTest(){
		SysDataSource ds= this.sqlExecutor.entityGetById(SysDataSource.class, 10000009651362L);
		System.out.println(	ds.getTables().size());
	}
	
	@Test
	public void entityQuery(){ 
		this.sqlExecutor.entityQuery(SysDataSource.class);
	}
	
	@Test
	public void entityQuery2(){ 
		List<SysDataSource > list=sqlExecutor.entityQuery(SysDataSource.class, "select * from  sys_datasource where 1=?", 1);
		System.out.println(list.size());
	}
	
	@Test
	public void entityQueryPage(){ 
		Page page=Page.getDefaultPage();
		sqlExecutor.entityQueryPage(Page.getDefaultPage(), SysDataSource.class);

		System.out.println(page.getEntityTable());
	}
	
	@Test
	public void entityQueryPage2(){
		Page page=Page.getDefaultPage();
		sqlExecutor.entityQueryPage(page,SysDataSource.class, "select * from  sys_datasource where 1=?", 1);
		
		System.out.println( page.getEntityTable().size() );
		
		
	}
}
