package org.lsqt.components.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * <pre>
 * 
 * 功能说明: 
 * 	  数据库工具类面向开发人员Test Connection
 * 
 * 编写日期:2011-4-21
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2012-3-10
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 */
public  class DataBaseUtil {
	private DataBaseUtil(){}
	
	/**
	 * 方法说明：执行数据库的连接
	 * @param driver
	 * @param url
	 * @param userName
	 * @param passwod
	 */
	public static boolean isConnected(String driver,String url, String userName, String passwod ){
		Connection con=null;
		try{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userName,passwod);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}finally{
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		return con!=null;
	}
	
	public static boolean isConnected(Connection conn){
		if(conn==null){
			return false;
		}
		
		System.out.println(conn);
		try {
			System.out.println("数据库链接是否关闭："+conn.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String ...st){
		try{
			
			Connection con=null;
			ResultSet rs=null;
			PreparedStatement pstmt=null;
			
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/oaonsite?&characterEncoding=utf-8&useUnicode=true&zeroDateTimeBehavior=round&autoReconnect=true&failOverReadOnly=false", "root", "123456");
				
				String sql="SELECT * FROM sys_dic where  itemName like '%普%' ";
				pstmt=con.prepareStatement(sql);
				rs= pstmt.executeQuery();
				while (rs.next()) {
					System.out.println(rs.getObject(1));
				}
				rs.close();
				pstmt.close();
				con.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
