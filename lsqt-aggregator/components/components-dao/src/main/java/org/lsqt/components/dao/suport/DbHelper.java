package org.lsqt.components.dao.suport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 * 
 * <pre>
 * 业务名:数据库访问工具类，提供常用的JDBC对象的资源回收方法或其它
 * 功能说明: 数据库访问工具类，提供常用的JDBC对象的资源回收方法或其它
 * 编写日期:	2011-5-10
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-10
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public final class DbHelper {
	/****/
	private static Logger logger=Logger.getLogger(DbHelper.class);
	
	/**
	 * 
	 * 方法说明：回收数据库连接对象，sql语句操作对象，结果集对象游标关闭等
	 *
	 * @param con 数据库连接对象(可以为空)
	 * @param stmt 数据库操作对象(可以为空)
	 * @param rs 数据库查询结果集对象引用(可以为空)
	 */
	public static void destroy(Connection con,Statement stmt,ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("数据库查询，结果集关闭失败, 错误信息 :"+e.getMessage());
			}finally{
				try {
					if(stmt != null)stmt.close();
				} catch (SQLException ex) {
					logger.error("数据库查询，Statement对象关闭失败, 错误信息 :"+ex.getMessage());
				}finally{
					try {
						if(con != null)con.close();
					} catch (SQLException ec) {
						logger.error("数据库连接，数据库连接对象关闭失败, 错误信息 :"+ec.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 方法说明：跟据jndi名称，查找数据源
	 *
	 * @param jndiName JNDI名称
	 * @return 返回数据源引用
	 */
	public static DataSource lookupDataSource(String jndiName) {
		try {
			Context context = new InitialContext();
			final String prifixStr="java:comp/env/";
			return (DataSource) context.lookup(prifixStr+jndiName);
		} catch (NamingException e) {
			throw new RuntimeException("Cannot find JNDI DataSource: ");
		}
	}
}
