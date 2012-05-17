package com.hirisun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;
import org.hibernate.cache.HashtableCacheProvider;
import org.hibernate.cache.OSCache;
public class TestConnection {
	
	@Test
	public void testConnection() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//Connection con=DriverManager.getConnection("jdbc:mysql://192.168.10.69:3306/test?useUnicode=true&characterEncoding=utf8","noah","noah");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8","mm","mm");
		//	Statement stmt= con.createStatement();
		//	ResultSet rs=stmt.executeQuery("select * from mytest");
			//rs.next();
			//System.out.println(rs.getString(1));
			System.out.println(con);
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
}
