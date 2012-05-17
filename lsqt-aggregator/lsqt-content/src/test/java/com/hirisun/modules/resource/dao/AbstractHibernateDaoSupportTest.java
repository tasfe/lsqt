package com.hirisun.modules.resource.dao;

import org.hibernate.FlushMode;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.ConfigHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.lsqt.content.model.User;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;


/**
 * 
 * @author mm
 *
 */
public class AbstractHibernateDaoSupportTest extends AbstractHibernateDaoSupport<User>{
	
	
	public AbstractHibernateDaoSupportTest(){
		AnnotationSessionFactoryBean annotationFactory=new  AnnotationSessionFactoryBean();
		annotationFactory.getHibernateProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.pool_size", "1");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.autocommit", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		annotationFactory.getHibernateProperties().setProperty("hibernate.hbm2ddl.auto", "create-drop");
		annotationFactory.getHibernateProperties().setProperty("hibernate.show_sql", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.current_session_context_class", "thread");
		
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:mydatabase");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.username", "sa");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.password", "");
		
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
	
	@Test
	public void testSave(){
		User user=new User();
		user.setEmail("kekekkek袁明敏e");
		super.executeHqlQuery("from User u where u.userId=?",new Object[]{"23"});
		this.save(user);
	
	}
}
