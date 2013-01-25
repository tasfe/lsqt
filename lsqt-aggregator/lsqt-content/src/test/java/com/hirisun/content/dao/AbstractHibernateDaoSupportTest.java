package com.hirisun.content.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.ConfigHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.model.User;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * 不需要启动整个应用程序,只提供持久层的HIBERNATE操作,用于HQL,Criteria等查询/更新语句.
 * @author 袁明敏
 *
 */
public class AbstractHibernateDaoSupportTest extends AbstractHibernateDaoSupport{
	private Transaction tran;
	private Session session;
	
	
	@Before
	public void init(){
		session=super.getSession();
		tran=session.beginTransaction();
		tran.begin();
	}
	
	@After
	public void destroy(){
		tran.commit();
		session.flush();
		session.close();
	}
	
	public AbstractHibernateDaoSupportTest(){
		
		AnnotationSessionFactoryBean annotationFactory=new  AnnotationSessionFactoryBean();
		/*
		annotationFactory.getHibernateProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		annotationFactory.getHibernateProperties().setProperty("hibernate.hbm2ddl.auto", "create-drop");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:mydatabase");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.username", "sa");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.password", "");
		*/
		
		annotationFactory.getHibernateProperties().setProperty("hibernate.dialect", "org.lsqt.content.dao.QTMySQLDialect");
		annotationFactory.getHibernateProperties().setProperty("hibernate.hbm2ddl.auto", "update");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/test");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.username", "root");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.password", "root");
		
		
		
		
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.pool_size", "1");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.autocommit", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		annotationFactory.getHibernateProperties().setProperty("hibernate.show_sql", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.current_session_context_class", "thread");
		

		
		annotationFactory.setPackagesToScan(new String[]{"org.lsqt.content.model"});
		try {
			annotationFactory.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.setSessionFactory(annotationFactory.getObject());
		
		/*Configuration cfg=new Configuration();
		cfg.setProperties(annotationFactory.getHibernateProperties());
		SchemaExport s=new SchemaExport(cfg);
		s.create(true,true);
		s.execute(true,true, true, true);
		//SchemaExport.main(new String []{"--create"});
*/	}
	
	//@Test
	public void testSave(){
		User user=new User();
		user.setEmail("kekekkek袁明敏e");
		super.executeHqlQuery("from User u where u.userId=?",new Object[]{"23"});
		this.save(user);
		
		/*
		String key="a";
		StringBuffer hql=new StringBuffer("from Application a where a.name like  ?  or a.description like ? ");
		session.createQuery(hql.toString()).setParameter(0,"'%"+key+"%'").setParameter(1,"'%"+key+"%'").setMaxResults(20).setFirstResult(2).list();
		*/
	}
}
