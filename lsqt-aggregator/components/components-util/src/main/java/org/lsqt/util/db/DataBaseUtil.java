package org.lsqt.util.db;

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
	
	public static void testConnection(String driver,String url, String userName, String passwod ){
		Connection con=null;
		try{
			Class.forName(driver);
			con=DriverManager.getConnection(url,userName,passwod);
			}catch(Exception e){
				e.printStackTrace();
				
			}finally{
				if(con!=null){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
	}
}
