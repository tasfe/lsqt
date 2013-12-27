package org.lsqt.components.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
public final class DataBaseUtil {
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
		return true;
	}
}
