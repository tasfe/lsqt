package org.lsqt.codegen.web.support;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.lsqt.components.dao.dbutils.SqlExecutor;

public class DataSourceUtil {
	
	public static DataSource getDataSource(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		Properties p=new Properties();
		p.put("driverClassName", "com.mysql.jdbc.Driver"); //com.mysql.jdbc.Driver
		p.put("username","root");
		p.put("password", "123456");
		p.put("url", "jdbc:mysql://localhost:3306/oaonsite?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=round&autoReconnect=true&failOverReadOnly=false");
		DataSource ds;
		
			ds = BasicDataSourceFactory.createDataSource(p);
			return ds;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
	}
}
