package org.lsqt.components.dao;

import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;



/**
 * 对象功能:系统数据源管理 Model对象
 * 开发公司:前海股权交易中心
 * 开发人员:Sky
 * 创建时间:2014-02-06
 */
@Table(name="sys_datasource",schema="oaonsite")
public class SysDataSource {
	
	/**
	 * 数据库类型=oracle
	 */
	public final static String DBTYPE_ORACLE = "oracle";
	/**
	 * 数据库类型=sql2005
	 */
	public final static String DBTYPE_SQL2005 = "sql2005";
	/**
	 * 数据库类型=mysql
	 */
	public final static String DBTYPE_MYSQL = "mysql";
	/**
	 * 数据库类型=db2
	 */
	public final static String DBTYPE_DB2= "db2";
	/**
	 * 数据库类型=h2
	 */
	public final static String DBTYPE_H2 = "h2";
	/**
	 * 数据库类型=达梦
	 */
	public final static String DBTYPE_DM= "dm";
	
	
	/***/
	private static final long serialVersionUID = -3760293574264972800L;
	// 主键
	@Id
	private Long id;
	
	// 数据源名称
	@Column(name="name")
	private String name;
	
	// 别名
	@Column(name="alias")
	private String alias;
	
	
	// 驱动名称
	@Column(name="driverName")
	private String driverName;
	
	// 数据库URL
	@Column(name="url")
	private String url;

	// 用户名
	@Column(name="userName")
	private String userName;
	
	// 密码
	@Column(name="password")
	private String password;
	
	//数据库类型
	@Column(name="dbType")
	private String dbType=DBTYPE_MYSQL;

	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回 数据源名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * 返回 别名
	 * @return
	 */
	public String getAlias() {
		return alias;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * 返回 驱动名称
	 * @return
	 */
	public String getDriverName() {
		return driverName;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 返回 数据库URL
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 返回 用户名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 返回 密码
	 * @return
	 */
	public String getPassword() {
		return password;
	}
}