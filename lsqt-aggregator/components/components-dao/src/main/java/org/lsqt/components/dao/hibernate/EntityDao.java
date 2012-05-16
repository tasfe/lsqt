package org.lsqt.components.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.suport.DataSet;
import org.lsqt.components.dao.suport.Page;


/**
 * 
 * <pre>
 * 业务名: 针对单个Entity的操作定义,不依赖于具体ORM实现方案
 * 所有实体层数据对象持久化访问接口
 * 功能说明: 
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            模型对象
 */
public interface EntityDao<T>
{

	/**
	 * 
	 * 方法说明：按类型删除所有对象
	 * 
	 */

	public void deleteAll();

	/**
	 * 跟据多个id批量删除对象
	 * 
	 * @param ids
	 *            一系列对象的id标识号
	 */

	public void deleteByIds(Serializable[] ids);

	/**
	 * 根据ID执行删除操作
	 */
	/**
	 * @param id
	 *            id
	 * @return delete
	 */

	public boolean deleteById(Serializable id);

	/**
	 * 查询有的对象
	 * 
	 * @return 返回对象列表
	 */

	public List<T> findAll();

	/**
	 * 查询所有对象，按照片 property 属性排序
	 * 
	 * @param property
	 *            对象属性名称
	 * @param isAsc
	 *            是否为升序
	 * @return 返回对象列表
	 */

	public List<T> findAll(String property, Boolean isAsc);

	/**
	 * 跟据id查询对象
	 * 
	 * @param id
	 *            对象标识
	 * @return 返回查询的对象值
	 */

	public T findById(Serializable id);

	/**
	 * 方法说明：跟据对象id号，批量查询对象
	 * 
	 * @param ids
	 *            对象id集合
	 * @return 返回查询的结果列表
	 */

	public List<T> findByIds(Serializable[] ids);

	/**
	 * 保存一个对象
	 * 
	 * @param e
	 *            要保存的实体对象
	 * @return bool
	 */

	public boolean save(T e);

	/**
	 * 删除一个对象
	 * 
	 * @param obj
	 *            要删除的实体对象
	 */

	public void delete(T obj);

	/**
	 * 更新一个实体对象
	 * 
	 * @param obj
	 *            要更新的实体对象
	 * @return 返回更新后的实体对象
	 */

	public T update(T obj);

	/**
	 * 按照对象的属性名称查找实体对象列表
	 * 
	 * @param property
	 *            实体对象的属性字符串
	 * @param value
	 *            属性值
	 * @return 返回按属性值匹配的结果集
	 */

	public List<T> findByProperty(String property, Object value);

	/**
	 * 方法说明:按照对象的属性名称查找实体对象列表
	 * 
	 * @param property
	 *            实体对象的属性字符串
	 * @param value
	 *            属性值
	 * @return 返回按属性值匹配的唯一值
	 */

	public T findByPropertyUnique(String property, Object value);

	/**
	 * 
	 * 方法说明：按照片对象查询查询示例，按属性模糊查询
	 * 
	 * @param obj
	 *            实体对象
	 * @return 返回匹配的结果集
	 */

	public List<T> findByExample(T obj);

	/**
	 * 加载分页对象
	 * 
	 * @param pageModel
	 *            初使化后不带数据的分页对象
	 * @return 返回带数据的分页对象
	 */

	public Page<T> loadPage(Page<T> pageModel);

	// ------------------------------------------------------- HQL: background
	// -----------------------------------
	/**
	 * 根据传过来的HQL命令执行更新操作
	 * 
	 * @param hql
	 *            查询语句
	 * @return boolean
	 */

	public boolean executeHql(String hql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return executehql
	 */

	public boolean executeHql(String hql, Object paramValues[]);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @return executehqlQuery
	 */

	public List<T> executeHqlQuery(String hql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return executehqlQuery
	 */

	public List<T> executeHqlQuery(String hql, Object paramValues[]);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @return uniqueResultbyHql
	 */

	public Object uniqueResultByHql(String hql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return uniqueResultbyHql
	 */

	public Object uniqueResultByHql(String hql, Object[] paramValues);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @return uniqueresultBySql
	 */

	public Object uniqueResultBySql(String sql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @return uniqueResultbySql
	 */

	public Object uniqueResultBySql(String sql, Object[] paramValues);

	// -------------------------------------------------------- SQL: background
	// ------------------------------------//
	/**
 * 
 */
	/**
	 * @param sql
	 *            sql
	 * @return executesql
	 */

	public boolean executeSql(String sql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValue
	 *            paramValue
	 * @return executesql
	 */

	public boolean executeSql(final String sql, final Object[] paramValue);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @return executesqlQuery
	 */

	@SuppressWarnings("unchecked")
	public List executeSqlQuery(String sql);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @return executeSqlquery
	 */

	@SuppressWarnings("unchecked")
	public List executeSqlQuery(String sql, Object[] paramValues);

	// -----------------------------------------------NamedQuery background
	// -----------------------------------------
	/**
 * 
 */
	/**
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @return executehqlNamedQuery
	 */

	public List<T> executeHqlNamedQuery(String hqlQueryName);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return executeHqlnamedQuery
	 */

	public List<T> executeHqlNamedQuery(String hqlQueryName, Object paramValues[]);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @return executeSqlnamedQuery
	 */

	@SuppressWarnings("unchecked")
	public List executeSqlNamedQuery(String sqlQueryName);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return executeSqlnamedQuery
	 */

	@SuppressWarnings("unchecked")
	public List executeSqlNamedQuery(String sqlQueryName, Object paramValues[]);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @return uniqueResultbyNamedHql
	 */

	public Object uniqueResultByNamedHql(String hqlQueryName);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return uniqueresultByNamedHql
	 */

	public Object uniqueResultByNamedHql(String hqlQueryName, Object paramValues[]);

	/**
	 * 
	 * 方法说明： 根据SQL查找到的名字获取唯一的结果
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @return uniqueresultByNamedSql
	 */

	public Object uniqueResultByNamedSql(String sqlQueryName);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return uniqueresultByNamedSql
	 */

	public Object uniqueResultByNamedSql(String sqlQueryName, Object paramValues[]);

	// ----------------------------------------------- background
	// -----------------------------------------
	/**
	 * 跟据sql加载分页对象
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @param page
	 *            page
	 * @return Page<Object[]>
	 */

	public Page<Object[]> loadPageBySql(String sql, Object[] paramValues, Page<Object[]> page);

	/**
	 * 
	 * 方法说明：hql分页查询
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @param pageModel
	 *            pageModel
	 * @return Page<T>
	 */

	public Page<T> loadPageByHql(String hql, Object[] paramValues, Page<T> pageModel);

	// -----------------------------------------存储过程执行-----------------
	/** 执行查询存储过程,返回结果集 **/
	/**
	 * @param procedureName
	 *            procedureName
	 * @return DataSet
	 */

	public DataSet excuteProcedure(final String procedureName);

	/** 执行只带输入参数的存储过程 **/
	/**
	 * 
	 */
	/**
	 * @param procedureName
	 *            procedureName
	 * @param paramValues
	 *            paramValues
	 * @return excuteProcedure excuteprocedure
	 */

	public DataSet excuteProcedure(final String procedureName, final Object[] paramValues);

	/**
	 * 存储过程调用 (返回结果集+输出参数+执行系列更新语句)
	 * 
	 * <PRE>
	 * 例如存储过程如下:
	 * 		call p_getvalue('11000112',@s)
	 * 
	 * JAVA调用范列: 
	 * 	Object [] paramValues=
	 * 
	 * 	DataSet ds2=this.userDao.excuteProcedure("p_getvalue", 
	 * 				new Object[]{"11000112",ParamType.newHolder()}, 
	 * 				new int[]{ParamType.VARCHAR,ParamType.VARCHAR});
	 * </PRE>
	 * **/
	/**
	 * @param procedureName
	 *            procedureName
	 * @param paramValues
	 *            paramValues
	 * @param paramValueTypes
	 *            paramValueTypes
	 * @return excuteprocedure
	 */
	public DataSet excuteProcedure(final String procedureName, final Object[] paramValues,
			final int[] paramValueTypes);
}
