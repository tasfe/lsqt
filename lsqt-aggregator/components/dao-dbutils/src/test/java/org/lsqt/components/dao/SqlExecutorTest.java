package org.lsqt.components.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lsqt.components.dao.dbutils.SqlExecutor;
import org.lsqt.components.dto.DataRow;
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
		p.put("driverClassName", "com.mysql.jdbc.Driver");
		p.put("username","root");
		p.put("password", "123456");
		p.put("url", "jdbc:mysql://localhost:3306/oaonsite");
		DataSource ds;
		try {
			ds = BasicDataSourceFactory.createDataSource(p);
			System.out.println(ds.getConnection().hashCode());
			System.out.println(ds.getConnection().hashCode());
			System.out.println(ds.getConnection().hashCode());
			System.out.println(ds.getConnection().hashCode());
			System.out.println(ds.getConnection().hashCode());
			//sqlExecutor=new SqlExecutor(ds);
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
	
	//@Test
	public void executeCRUDTest(){
		
		
		int cnt=sqlExecutor.executeSql("CREATE TABLE TBL_USERS(ID INTEGER, NAME varchar(50), BIRTHDAY DATE)");
		Assert.assertEquals(0, cnt);
		
		
		cnt=sqlExecutor.executeSql("INSERT INTO TBL_USERS(ID, NAME, BIRTHDAY) VALUES ('1', 'ADMIN', SYSDATE)");
		Assert.assertTrue(cnt>0);
		
		DataTable dt=sqlExecutor.executeQuery("select * from TBL_USERS");
		
		List<DataRow> list=dt.getDataRows();
		for(DataRow r:list){
			System.out.println(Arrays.asList(r.toArray()));
		}
		
		cnt=sqlExecutor.executeSql("delete from TBL_USERS where id=1");
		Assert.assertEquals(1, cnt);
		 
	}
	
	//@Test
	public void executePageTest(){
		for(int i=1;i<100;i++){
			sqlExecutor.executeSql("INSERT INTO TBL_USERS(ID, NAME, BIRTHDAY) VALUES ('"+i+"', 'ADMIN_"+i+"', SYSDATE)");
		}

		
		Page page=new Page(30,2);
		sqlExecutor.executeQueryPage("select * from TBL_USERS where id in(1,2,3,4,5,6,7,8,9,10) and id=? ",new Object[]{11} ,page);
		System.out.println(page);
	
	}
	
	@Test
	public void executePageTest2(){
		Page page=new Page(1,3);//每页1条，显示第3页
		
		String sql="SELECT * FROM sys_sms where userId = ? and systemId=? order by id desc";
		
		sqlExecutor.executeQueryPage(sql, new Object[]{21,1},page);
		System.out.println(page);
	}
}
