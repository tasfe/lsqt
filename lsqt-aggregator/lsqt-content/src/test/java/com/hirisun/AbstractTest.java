package com.hirisun;

import java.util.UUID;

import junit.framework.TestCase;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.TestClass;
import org.lsqt.content.service.UserService;
import org.lsqt.content.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.hibernate.dialect.H2Dialect;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;
import  org.springframework.web.util.Log4jConfigListener;
public class AbstractTest{
	/**
	 * 
	 */
	protected static ApplicationContext configs;
	/**
	 * 
	 */
	private static SessionFactory sessionFactory;

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param beanName
	 * @return
	 */
	protected static Object getBean(String beanName) {
		
		return configs.getBean(beanName);
	}
	
	protected static <T> T getBean(Class<T> requiredType){
		return configs.getBean(requiredType);
	}

	/**
	 * 
	 * @param <T>
	 * @param beanName
	 * @param requiredType
	 * @return
	 */
	protected static <T> T  getBean(String beanName,Class<T> requiredType){
		return configs.getBean(beanName, requiredType);
	}
	
	@BeforeClass
	public static void init() {
		configs = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml" });
		
		
		
		
		
	}
	
	@AfterClass
	public static void destroy() {
		
	}

	@Before
	public void invokeBefore() {
		sessionFactory = (SessionFactory) getBean("sessionFactory");
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(s));
	}
	@After
	public void invokAfter() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.closeSession(holder.getSession());
	}
	
}
