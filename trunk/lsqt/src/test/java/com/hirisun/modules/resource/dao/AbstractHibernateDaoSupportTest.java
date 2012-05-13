package com.hirisun.modules.resource.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.content.model.User;

/**
 * 
 * @author mm
 *
 */
public class AbstractHibernateDaoSupportTest extends AbstractHibernateDaoSupport<User>{
	
	
	public AbstractHibernateDaoSupportTest(){
		AnnotationSessionFactoryBean annotationFactory=new  AnnotationSessionFactoryBean();
		annotationFactory.getHibernateProperties().setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.pool_size", "1");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.autocommit", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		annotationFactory.getHibernateProperties().setProperty("hibernate.hbm2ddl.auto", "create-drop");
		annotationFactory.getHibernateProperties().setProperty("hibernate.show_sql", "true");
		annotationFactory.getHibernateProperties().setProperty("hibernate.current_session_context_class", "thread");
		
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:mydatabase");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.username", "sa");
		annotationFactory.getHibernateProperties().setProperty("hibernate.connection.password", "");
		
		annotationFactory.setPackagesToScan(new String[]{"com.lsqt.modules.resource.*"});
		try {
			annotationFactory.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateTemplate template=new HibernateTemplate(annotationFactory.getObject());
		super.setSessionFactory(annotationFactory.getObject());
		//super.setHibernateTemplate(template);
		template.afterPropertiesSet();
	}
	
	@Test
	public void testSave(){
		
		
		User user=new User();
		user.setEmail("kekekkeke");
		save(user);
		
		super.executeHqlQuery("from User u where u.userId=?",new Object[]{"23"});
		//getHibernateTemplate().save(user);
		//this.save(user);
	}
}
