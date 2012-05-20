package com.hirisun.content.web.temptest;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class AcessDBConnectionTest {
	
	//@Test
	public  void getConnection(){
		Connection con = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			String path = this.getClass().getClassLoader().getResource("/test.mdb").getPath().substring(1);
			String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+ path;
			con = DriverManager.getConnection(url, "", "");
			System.out.println(con+"连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
