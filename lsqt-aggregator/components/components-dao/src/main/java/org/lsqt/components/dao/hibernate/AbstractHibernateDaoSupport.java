package org.lsqt.components.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import org.lsqt.components.dao.DaoException;
import org.lsqt.components.dao.suport.BeanHelper;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.DataRow;
import org.lsqt.components.dao.suport.DataSet;
import org.lsqt.components.dao.suport.DataTable;
import org.lsqt.components.dao.suport.DbHelper;
import org.lsqt.components.dao.suport.HibernateTypeHelper;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.components.dao.suport.ParamType;


/**
 * 所有数据库持久层dao抽象基类
 * 数据源会话工厂注入默认以sessionFactory的spring bean命名注入
 * @author 袁明敏
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
 * 
 * @version 1.1
 * @since 2010-07-01
 * 
 * @param <T>
 * 
 */

@SuppressWarnings("unchecked")
@Repository
public abstract class AbstractHibernateDaoSupport<T> extends HibernateDaoSupport implements EntityDao<T> {
	
	@Autowired
	protected void setSessionFactoryOverride(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public int executeUpdate(final String sql, final Object... params) {
		this.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pstmt=null;
				try{
					pstmt= connection.prepareStatement(sql);
				for(int i=0;i<params.length;i++){
					pstmt.setObject(i+1, params[i]);
				}
				 pstmt.executeUpdate(sql);
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					DbHelper.destroy(null, pstmt, null);
				}
			}
		});
		return 0;
	}
	
	@Override
	public Object executeQuery(final String sql, final Object... params) {
		final DataSet DATASET = new DataSet();
		this.getSession().doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					pstmt = connection.prepareStatement(sql);
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}

					boolean hasResults = pstmt.execute();

					while (hasResults) {
						DataTable dt = new DataTable();

						rs = pstmt.getResultSet();
						int colCount = rs.getMetaData().getColumnCount();
						while (rs.next()) {
							DataRow row = new DataRow();
							for (int i = 0; i < colCount; i++) {
								row.add(rs.getObject(i + 1));
							}
							dt.add(row);
						}
						DATASET.add(dt);
						hasResults = pstmt.getMoreResults();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DbHelper.destroy(null, pstmt, rs);
				}
			}
		});
		return DATASET;
	}
	
	/** 日志对象 **/
	private static final Logger logger = Logger.getLogger(AbstractHibernateDaoSupport.class);
	/****/
	private Class currClass;
	{
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type[] types = pType.getActualTypeArguments();
			currClass = (Class) types[0];
			LogFactory.getLog(currClass);
		}
	}

	/**
	 * 
	 * 方法说明：按类型删除所有对象
	 * 
	 */
	
	public void deleteAll() {
		try {
			this.getSession().createQuery("delete " + currClass.getName()).executeUpdate();
			
		} catch (Exception ex) {
			logger.error("delete " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("delete All " + currClass.getName() + "  is success!");
	}

	/**
	 * 跟据多个id批量删除对象
	 * 
	 * @param ids  一系列对象的id标识号
	 */
	
	public void deleteByIds(Serializable[] ids) {
		try {
			Session session = this.getSession();
			int cnt = 0;
			for (Serializable i : ids) {
				Object temp = session.get(currClass, i);
				if(temp!=null){
					session.delete(temp);
				}
				cnt++;
				if (cnt > 500) {
					session.flush();
					cnt = 0;
				}
			}
		} catch (Exception ex) {
			logger.error("deleteByIds " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("deleteByIds " + currClass.getName() + " success!");
	}

	/**
	 * 根据ID执行删除操作
	 * @param id 对象标识
	 */
	public boolean deleteById(Serializable id) {
		try {
			Session session = this.getSession();
			Object persistence = session.get(currClass, id);
			if (persistence != null) {
				session.delete(persistence);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查询有的对象
	 * 
	 * @return 返回对象列表
	 */
	
	public List<T> findAll() {
		List<T> list = null;
		try {
			list = this.getSession().createCriteria(currClass).list();
		} catch (Exception ex) {
			logger.error("findAll " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("findAll " + currClass.getName() + " success!");
		return list;
	}

	/**
	 * 查询所有对象，按照片 property 属性排序
	 * 
	 * @param property 对象属性名称
	 * @param isAsc 是否为升序
	 * @return 返回对象列表
	 */
	
	public List<T> findAll(String property, Boolean isAsc) {
		try {
			Criteria criteria = this.getSession().createCriteria(currClass);
			if (isAsc) {
				List<T> list = criteria.addOrder(Order.asc(property)).list();
				if (list != null && list.size() > 0) {
					logger.info("findAll " + currClass.getName() + " success!");
				} else {
					if (list == null) {
						logger.warn("findAll " + currClass.getName() + " success,but returned list is null !");
					} else {
						logger.warn("findAll " + currClass.getName() + " success,but returned list size==0 !");
					}
				}
				return list;
			} else {
				return criteria.addOrder(Order.desc(property)).list();
			}

		} catch (Exception ex) {
			logger.error("findAll " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}

	}

	/**
	 * 跟据id查询对象
	 * 
	 * @param id 对象标识
	 * @return 返回查询的对象值
	 */
	
	public T findById(Serializable id) {
		T model = null;
		try {
			model = (T) this.getSession().get(currClass, id);
		} catch (Exception ex) {
			logger.error("findById " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		
		if(model!=null){
			logger.info("findById " + currClass.getName() + " success ,object id is {" + id + "}!");
		}
		return model;
	}

	/**
	 * 方法说明：跟据对象id号，批量查询对象
	 * 
	 * @param ids 对象id集合
	 * @return 返回查询的结果列表
	 */
	
	public List<T> findByIds(Serializable[] ids) {
		List<T> list  = new ArrayList<T>();;
		try {
			Session session = this.getSession();
			
			for (Serializable i : ids) {
				T temp = (T) session.get(currClass, i);
				list.add(temp);
			}
		} catch (Exception ex) {
			logger.error("findByIds " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}

		if(list.size()>0){
			logger.info("findByIds " + currClass.getName() + " success!");
		}
		return list;
	}

	/**
	 * 保存一个对象
	 * 
	 * @param e 要保存的实体对象
	 * @return boolean 保存成功返回true
	 */
	public boolean save(T e) {
		Serializable identifier = this.getSession().save(e);
		logger.info("save " + currClass.getName() + " success,it's identifier : "+identifier+" !");
		if (identifier != null) {
			return true;
		}
		return false;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param obj 要删除的实体对象
	 */
	
	public void delete(T obj) {
		try {
			this.getSession().delete(obj);
		} catch (Exception ex) {
			logger.error("delete " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("delete " + currClass.getName() + " success!");
	}

	/**
	 * 更新一个实体对象
	 * 
	 * @param obj 要更新的实体对象
	 * @return 返回更新后的实体对象
	 */
	
	public T update(T obj) {
		try {
			obj = (T) this.getSession().merge(obj);
		} catch (Exception ex) {
			logger.error("update " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("update " + currClass.getName() + " success!");
		return obj;
	}

	/**
	 * 按照对象的属性名称查找实体对象列表
	 * 
	 * @param property 实体对象的属性字符串
	 * @param value  属性值
	 * @return 返回按属性值匹配的结果集
	 */
	
	public List<T> findByProperty(String property, Object value) {
		List<T> list = null;
		try {
			list = this.getSession().createCriteria(currClass).add(Restrictions.eq(property, value)).list();
		} catch (Exception ex) {
			logger.error("findByProperty " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("findByProperty " + currClass.getName() + " success!");
		return list;
	}

	/**
	 * 方法说明:按照对象的属性名称查找实体对象列表
	 * 
	 * @param property 实体对象的属性字符串
	 * @param value 属性值
	 * @return 返回按属性值匹配的唯一值
	 */
	
	public T findByPropertyUnique(String property, Object value) {
		T t = null;
		try {
			t = (T) this.getSession().createCriteria(currClass).add(Restrictions.eq(property, value)).uniqueResult();
		} catch (Exception ex) {
			logger.error("findByProperty " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		return t;
	}

	/**
	 * 
	 * 方法说明：按照片对象查询查询示例，按属性模糊查询
	 * 
	 * @param obj 实体对象
	 * @return 返回匹配的结果集
	 */
	
	public List<T> findByExample(T obj) {
		List results = this.getSession().createCriteria(currClass).add(Example.create(obj).ignoreCase().enableLike(MatchMode.ANYWHERE)).list();
		return results;
	}

	




	// -------------------------- HQL: background-------------------------
	/**
	 * 方法说明：根据HQL语句执行更新
	 * 
	 * @param hql 查询语句
	 * @return boolean 执行成功返回true
	 */
	
	public boolean executeHql(String hql) {
		return this.getSession().createQuery(hql).executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：执行hql更新
	 * 
	 * @param hql hql更新语句
	 * @param paramValues 占位符参数值
	 * @return boolean　执行成功返回true
	 */
	
	public boolean executeHql(String hql, Object paramValues[]) {
		Query query = this.getSession().createQuery(hql);
		return processParamValues(paramValues, query).executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：执行hql查询
	 * 
	 * @param hql hql查询语句
	 * @return List<T> 返回对象列表
	 */
	
	public List<T> executeHqlQuery(String hql) {
		return this.getSession().createQuery(hql).list();
	}

	/**
	 * 
	 * 方法说明：执行带占位符的hql查询
	 * 
	 * @param hql hql查询语句
	 * @param paramValues 占位符参数值
	 * @return List<T> 返回对象列表
	 */
	
	public List<T> executeHqlQuery(String hql, Object paramValues[]) {
		Query query = this.getSession().createQuery(hql);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：hql的唯一结果查询
	 * 
	 * @param hql hql查询(聚合)语句
	 * @return Object　返回聚合值或唯一结果值
	 */
	
	public Object uniqueResultByHql(String hql) {
		return this.getSession().createQuery(hql).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：带占位符的hql唯一结果查询
	 * 
	 * @param hql hql查询(聚合)语句
	 * @param paramValues 占位符参数值
	 * @return Object 返回聚合值或唯一结果值
	 */
	
	public Object uniqueResultByHql(String hql, Object[] paramValues) {
		Query query = this.getSession().createQuery(hql);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：sql唯一结果查询
	 * 
	 * @param sql sql查询(聚合)语句
	 * @return Object 返回聚合值或唯一结果值
	 */
	
	public Object uniqueResultBySql(String sql) {
		Query query = this.getSession().createSQLQuery(sql);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：带占位符的sql唯一结果查询
	 * 
	 * @param sql sql查询(聚合)语句
	 * @param paramValues 占位符参数值
	 * @return Object 返回聚合值或唯一结果值
	 */
	
	public Object uniqueResultBySql(String sql, Object[] paramValues) {
		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	// ---------------------------- SQL: background----------------------------//
	/**
	 * 方法说明:执行sql更新语句
	 * 
	 * @param sql sql更新语句
	 * @return boolean　执行成功返回true
	 */
	
	public boolean executeSql(String sql) {
		int cnt = this.getSession().createSQLQuery(sql).executeUpdate();
		return cnt > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql sql更新语句
	 * @param paramValue 占位符参数值
	 * @return boolean　执行成功返回true
	 */
	
	public boolean executeSql(final String sql, final Object[] paramValue) {
		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValue, query);
		return query.executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql　sql查询语句
	 * @return List　返回结果集
	 */
	
	public List executeSqlQuery(String sql) {
		return this.getSession().createSQLQuery(sql).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql　sql查询语句
	 * @param paramValues 占位符参数值
	 * @return List 返回结果集
	 */
	
	public List executeSqlQuery(String sql, Object[] paramValues) {
		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	// ------------------------NamedQuery background  -------------------------
	/**
	 * @param hqlQueryName 命名查询
	 * @return List<T> 返回结果集
	 */
	
	public List<T> executeHqlNamedQuery(String hqlQueryName) {
		return this.getSession().getNamedQuery(hqlQueryName).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 * @param paramValues
	 * @return List<T>
	 */
	
	public List<T> executeHqlNamedQuery(String hqlQueryName, Object paramValues[]) {
		Query query = this.getSession().getNamedQuery(hqlQueryName);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 * @return List
	 */
	
	public List executeSqlNamedQuery(String sqlQueryName) {
		return this.getSession().getNamedQuery(sqlQueryName).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 * @param paramValues
	 * @return List
	 */
	
	public List executeSqlNamedQuery(String sqlQueryName, Object paramValues[]) {
		Query query = this.getSession().getNamedQuery(sqlQueryName);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 * @return Object
	 */
	
	public Object uniqueResultByNamedHql(String hqlQueryName) {
		return this.getSession().getNamedQuery(hqlQueryName).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 * @param paramValues
	 * @return Object
	 */
	
	public Object uniqueResultByNamedHql(String hqlQueryName, Object paramValues[]) {
		Query query = this.getSession().getNamedQuery(hqlQueryName);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明： 根据SQL查找到的名字获取唯一的结果
	 * 
	 * @param sqlQueryName
	 * @return Object
	 */
	
	public Object uniqueResultByNamedSql(String sqlQueryName) {
		return this.getSession().getNamedQuery(sqlQueryName).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 * @param paramValues
	 * @return uniqueresultByNamedSql
	 */
	
	public Object uniqueResultByNamedSql(String sqlQueryName, Object paramValues[]) {
		Query query = this.getSession().getNamedQuery(sqlQueryName);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：私有方法，处理参数值
	 * 
	 * @param paramValues
	 * @param query
	 * @return query
	 */
	private Query processParamValues(Object[] paramValues, Query query) {
		for (int i = 0; i < paramValues.length; i++) {
			query = query.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
		}
		return query;
	}

	// ---------------------------------- background---------------------------------
	
	/**
	 * 处理总记录数.
	 * @param criteria 查询对象
	 * @return 总记录数
	 */
	private int processTotalRcords(Criteria criteria){
		Object totalRecordObj = criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		if (totalRecordObj != null) {
			return  Integer.valueOf(totalRecordObj.toString());
		}else{
			return 0;
		}
	}
	
	/**
	 * 处理条件.
	 * @param criteria 查询对象
	 * @param condition 条件对象
	 */
	private void processCondition(Criteria criteria,Condition condition){
		if (condition != null) {
			List<Criterion> list = (List<Criterion>) BeanHelper.forceGetProperty(condition, "expressions");
			for (Criterion i : list) {
				criteria = criteria.add(i);
			}
		}
	}
	
	/**
	 * 处理排序.
	 * @param orderProps 排序属性
	 * @param criteria 查询对象
	 */
	private void processOrder(List orderProps, Criteria criteria) {
		// 排序
		boolean isEmpty = orderProps.isEmpty();
		if (isEmpty == false) {
			
			for (int i = 0; i < orderProps.size(); i++) {
				List temp = (List) orderProps.get(i);
				if (temp.size() == 2) {
					String key = (String) temp.get(0);
					Boolean value = (Boolean) temp.get(1);
					if (value == true) {
						criteria = criteria.addOrder(Order.asc(key));
					} else {
						criteria = criteria.addOrder(Order.desc(key));
					}
				}
			}
		}
	}
	
	/**
	 * 处理当前页是否越界.
	 * @param page 分页bean
	 * @param totalRecord　总记录数
	 */
	private void processCurrPageNum(Page page,int totalRecord){
		if (page.getCurrPageNum() * page.getPerPageRecord() >= totalRecord) {
			double t = (0.0D + totalRecord)
					/ (0.0D + page.getPerPageRecord());
			int endP = Double.valueOf(Math.ceil(t)).intValue();
			page.setCurrPageNum(endP);
		}
	}
	

	/**
	 *处理分页对象内部属性值.
	 * 
	 * @param pageModel 分页模型
	 */
	private void processPageBeanProperties(Page page,int totalRecord,int totalPage,Collection data) {
		BeanHelper.forceSetProperty(page, "totalRecord", totalRecord);
		
		BeanHelper.forceSetProperty(page, "totalPage",totalPage );
		
		BeanHelper.forceSetProperty(page, "data", data);
		
		
		if (page.getCurrPageNum() >= page.getTotalPage()) {
			BeanHelper.forceSetProperty(page, "currPageNum", page.getTotalPage());
			BeanHelper.forceSetProperty(page, "hasNextPage", false);
		} else {
			BeanHelper.forceSetProperty(page, "hasNextPage", true);
		}

		if (page.getCurrPageNum() <= 1) {
			BeanHelper.forceSetProperty(page, "currPageNum", 1);
			BeanHelper.forceSetProperty(page, "hasPreviouPage", false);
		} else {
			BeanHelper.forceSetProperty(page, "hasPreviouPage", true);
		}

	}
	
	/**
	 * 加载分页对象
	 * 
	 * @param page 初使化后不带数据的分页对象
	 * @return 返回带数据的分页对象
	 */
	public Page loadPage(Page page) {
		try {
			Criteria criteria = getSession().createCriteria(currClass);
			Criteria totalCriteria = getSession().createCriteria(currClass);


			processCondition(totalCriteria, page.getConditions());
			
			processCondition(criteria, page.getConditions());
			
			int totalRecord=processTotalRcords(totalCriteria);
			
			
			processCurrPageNum(page,totalRecord);
			
			
			int firstResult = (page.getCurrPageNum() - 1) * page.getPerPageRecord();
			int maxResult = page.getPerPageRecord();
			
			
			criteria.setFirstResult(firstResult);  //the first result to retrieve, numbered from 0
			criteria.setMaxResults(maxResult);
			
			processOrder(page.getOrderProperties(), criteria);

			List list = criteria.list();

			
			double temp = Double.valueOf(totalRecord)
					/ Double.valueOf(page.getPerPageRecord());
			int totalPage = Double.valueOf(Math.ceil(temp)).intValue();
			
			processPageBeanProperties(page, totalRecord, totalPage, list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		logger.debug("load successful");
		return page;
	}
	
	/**
	 * 
	 * 方法说明：HQL分页查询.
	 * 
	 * @param hql 不带占位符的查询
	 * @param page 分页bean
	 * @return Page 返回带数据的分页bean
	 */
	public Page loadPageByHql(String hql,  Page page) {
		Object[] t=new Object[]{};
		return  loadPageByHql(hql, t, page) ;
	}
	
	/**
	 * 处理HQL分页查询总计录数的HQL语句.
	 * @param hql
	 * @return
	 */
	protected static String processHQLCountStr(String hql){
		StringBuffer hqlTemp=new StringBuffer();
		
		if(! hql.startsWith("from ")){
			String [] temp=hql.split(" ");
			int flag=-1;
			for(int i=0;i<temp.length;i++){
				if("from".equals(temp[i])){
					hqlTemp.append(temp[i]+" ");
					flag=1;
				}else{
					if(flag==1){
						hqlTemp.append(temp[i] +" ");
					}
				}
			}
		}else{
			hqlTemp.append(hql);
		}
		
		hql=hqlTemp.toString();
		hql="select count(*) "+hql;
		return hql;
	}
	
	public static void main(String args[]){
		String sql="from News n left join n.categories  c where c.id= ?   ";
		
		System.out.println(processHQLCountStr(sql));
	}
	/**
	 * 
	 * 方法说明：HQL分页查询.
	 * 
	 * @param hql 带占位符的查询
	 * @param paramValues 参数值
	 * @param page 分页bean
	 * @return Page 返回带数据的分页bean
	 */
	public Page loadPageByHql(String hql, Object[] paramValues, Page page) {
		try {
	
			Query query = getSession().createQuery(hql);
			Query totalQuery=getSession().createQuery(processHQLCountStr(hql));
			
			for (int i = 0; i < paramValues.length; i++) {
				query.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
				totalQuery.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
			}
			
			
			Object totalObj= totalQuery.uniqueResult();
			int totalRecord= Double.valueOf(totalObj==null ?  "0":totalObj.toString()).intValue();
			
			
			
			processCurrPageNum(page, totalRecord);
			
			int firstResult = (page.getCurrPageNum() - 1) * page.getPerPageRecord();
			int maxResult = page.getPerPageRecord();
			
			query.setMaxResults(maxResult);
			query.setFirstResult(firstResult);
			List data = query.list();
		


			
			double temp = Double.valueOf(totalObj == null ? "0" : totalObj
					.toString()) / Double.valueOf(page.getPerPageRecord());
			int totalPage=Double.valueOf(Math.ceil(temp)).intValue();
		 

			processPageBeanProperties(page,totalRecord,totalPage,data);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return page;
	}

	/**
	 * 跟据原生SQL加载分页.
	 * 
	 * @param sql 带占位符的原生SQL语句
	 * @param page　分页bean
	 * @return Page<Object[]>　返回带数据的分页bean
	 */
	public Page<Object[]> loadPageBySql(String sql, Page<Object[]> page) {
		Object[] t=new Object[]{};
		return  loadPageBySql(sql,t, page);
	}
	
	/**
	 * 跟据原生SQL加载分页.
	 * 
	 * @param sql 带占位符的原生SQL语句
	 * @param paramValues　参数值
	 * @param page　分页bean
	 * @return Page<Object[]>　返回带数据的分页bean
	 */
	public Page<Object[]> loadPageBySql(String sql, Object[] paramValues, Page<Object[]> page) {
		try {
		

		Query query = super.getSession().createSQLQuery(sql);
		Query totalQuery = super.getSession().createSQLQuery(
				"select count(*) from ( ".concat(sql).concat(" ) _t")
						.concat(String.valueOf(RandomUtils.nextInt()))); 
		// nested  query  support MySQL alias
		
		for (int i = 0; i < paramValues.length; i++) {
			query.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
			totalQuery.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
		}
		
		Object totalObj=totalQuery.uniqueResult();
		
		int totalRecord= Double.valueOf(totalObj==null ?  "0":totalObj.toString()).intValue();
		
		processCurrPageNum(page, totalRecord);
		
		int firstResult = (page.getCurrPageNum() - 1) * page.getPerPageRecord();
		int maxResult = page.getPerPageRecord();
		
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		List list = query.list();

	
		double temp = Double.valueOf(  totalObj== null ? "0": totalObj.toString() ) / Double.valueOf(page.getPerPageRecord());
		int totalPage=Double.valueOf(Math.ceil(temp)).intValue();
		
		
		processPageBeanProperties(page,totalRecord,totalPage,list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return page;
	}



	// -----------------------------------------存储过程执行-----------------
	/**
	 * 执行查询存储过程,返回结果集
	 * 
	 * @param procedureName
	 * @return DataSet
	 */
	
	public DataSet excuteProcedure(final String procedureName) {
		final DataSet DATASET = new DataSet();
		this.getSession().doWork(new Work() {
			public void execute(Connection con) throws SQLException {

				CallableStatement cstmt = null;
				ResultSet rs = null;
				try {
					cstmt = con.prepareCall("{call " + procedureName + "()}");

					boolean hasResults = cstmt.execute();

					while (hasResults) {
						DataTable dt = new DataTable();

						rs = cstmt.getResultSet();// ---取得第一个结果集
						int colCount = rs.getMetaData().getColumnCount();
						while (rs.next()) {
							DataRow row = new DataRow();
							for (int i = 0; i < colCount; i++) {
								row.add(rs.getObject(i + 1));
							}
							dt.add(row);
						}
						DATASET.add(dt);
						hasResults = cstmt.getMoreResults();// ----继续去取结果集，若还还能取到结果集，则bl=true了。然后回去循环。
					}
				} catch (Exception e) {
					con.rollback();
					e.printStackTrace();
				} finally {
					DbHelper.destroy(null, cstmt, rs); // 连接的关闭让hibernate去管理
				}
			}
		});
		return DATASET;
	}

	/**
	 * @param paramValues paramValues
	 * @return sql_hold_string
	 */
	private String processProcedureParamHold(Object[] paramValues) {
		String holdString = "";
		int holdLength = paramValues.length;
		for (int i = 0; i < holdLength; i++) {
			if (i != holdLength - 1) {
				holdString = "?," + holdString;
			} else {
				holdString = holdString + "?";
			}
		}
		System.out.println(holdString);
		return holdString;
	}

	/**
	 * 方法说明:执行存储过程
	 * 
	 * @param procedureName
	 * @param paramValues
	 * @return DataSet
	 */
	
	public DataSet excuteProcedure(final String procedureName, final Object[] paramValues) {
		final DataSet DATASET = new DataSet();
		this.getSession().doWork(new Work() {
			public void execute(Connection con) throws SQLException {

				CallableStatement cstmt = null;
				ResultSet rs = null;
				try {
					cstmt = con.prepareCall("{call " + procedureName + "(" + processProcedureParamHold(paramValues) + ")}");

					boolean hasResults = cstmt.execute();

					while (hasResults) {
						DataTable dt = new DataTable();

						rs = cstmt.getResultSet();// ---取得第一个结果集
						int colCount = rs.getMetaData().getColumnCount();
						while (rs.next()) {
							DataRow row = new DataRow();
							for (int i = 0; i < colCount; i++) {
								row.add(rs.getObject(i + 1));
							}
							dt.add(row);
						}
						DATASET.add(dt);
						hasResults = cstmt.getMoreResults();// ----继续去取结果集，若还还能取到结果集，则hasResults=true了。然后回去循环。
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DbHelper.destroy(null, cstmt, rs); // 连接的关闭让hibernate去管理
				}
			}
		});
		return DATASET;
	}

	/**
	 * @param procedureName
	 * @param paramValues
	 * @param paramValueTypes
	 * @return DataSet
	 * 
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
	public DataSet excuteProcedure(final String procedureName, final Object[] paramValues, final int[] paramValueTypes) {
		if (paramValues.length != paramValueTypes.length) {
			try {
				throw new DaoException("参数值和类型指定,不匹配");
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}

		final DataSet DATASET = new DataSet();

		this.getSession().doWork(new Work() {
			public void execute(Connection con) throws SQLException {
				String sqlHold = processProcedureParamHold(paramValues);
				CallableStatement cstmt = null;
				ResultSet rs = null;
				try {

					cstmt = con.prepareCall("{call " + procedureName + "(" + sqlHold + ")}");

					List<Integer> outputParamIndex = new ArrayList<Integer>(); // 输出参数索引
					for (int i = 0; i < paramValues.length; i++) {
						if (paramValues[i] == ParamType.HOLDER) {
							cstmt.registerOutParameter(i + 1, paramValueTypes[i]);
							outputParamIndex.add(i + 1);
						} else {
							cstmt.setObject(i + 1, paramValues[i]);
						}
					}

					boolean hasResults = cstmt.execute();

					// 处理输出参数值
					List<Object> outputValues = new ArrayList<Object>();
					for (int i = 0; i < outputParamIndex.size(); i++) {
						outputValues.add(cstmt.getObject(outputParamIndex.get(i)));
					}

					DATASET.setOutputParams(outputValues.toArray(new Object[outputValues.size()]));

					while (hasResults) {
						DataTable dt = new DataTable();

						rs = cstmt.getResultSet();// ---取得第一个结果集
						int colCount = rs.getMetaData().getColumnCount();
						while (rs.next()) {
							DataRow row = new DataRow();
							for (int i = 0; i < colCount; i++) {
								row.add(rs.getObject(i + 1));
							}
							dt.add(row);
						}
						DATASET.add(dt);
						hasResults = cstmt.getMoreResults();
					}
				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					DbHelper.destroy(null, cstmt, rs); // 连接的关闭让hibernate去管理
				}
			}
		});
		return DATASET;
	}
}