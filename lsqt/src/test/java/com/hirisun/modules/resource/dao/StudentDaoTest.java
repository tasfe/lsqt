package com.hirisun.modules.resource.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.hirisun.AbstractTest;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.service.UserService;

public class StudentDaoTest extends AbstractTest{
	private static final Logger LOGGER = Logger.getLogger(StudentDaoTest.class);
	@Test
	public void testSessionFactory(){
		UserService us=(UserService)getBean("userService");
		User user=new User();
		user.setEmail("keke@hirisun.com");
		us.saveUser(user);
		
		SessionFactory facotry=getBean("sessionFactory", SessionFactory.class);
		LOGGER.debug(facotry);
		Assert.assertNotNull(facotry);
	}
	
	//@Test
	public void testSessionFactory2() throws SQLException{
		DataSource dao= getBean("dataSource",DataSource.class);
		Assert.assertNotNull(dao.getConnection());
	}
	
}
