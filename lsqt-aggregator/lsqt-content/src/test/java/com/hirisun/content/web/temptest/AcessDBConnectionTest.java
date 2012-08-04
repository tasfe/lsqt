package com.hirisun.content.web.temptest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;
public class AcessDBConnectionTest {
	
	@Test
	public  void getConnection(){
		Connection con = null;
		try {
	        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	        String url = "jdbc:odbc:Driver={Microsoft Access Driver " +
	            "(*.mdb,*.accdb)};DBQ=/home/mm/lsqt20120802.accdb;PWD=20120802";
	         con = DriverManager.getConnection(url);
	        System.out.println("Connected!");
	        con.close();
	        } catch (SQLException e) {
	            System.out.println("SQL Exception: "+ e.toString());
	        } catch (ClassNotFoundException cE) {
	            System.out.println("Class Not Found Exception: "+
	                cE.toString());
	        }
	}
}
