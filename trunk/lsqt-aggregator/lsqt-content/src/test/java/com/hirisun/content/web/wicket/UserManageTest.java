package com.hirisun.content.web.wicket;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.easymock.MockControl;
import org.junit.Test;
import org.lsqt.content.dao.UserDao;
import org.lsqt.content.model.User;
import org.lsqt.content.service.UserService;
import org.lsqt.content.service.UserServiceImpl;
import org.lsqt.content.web.wicket.UserManage;
import org.springframework.aop.support.annotation.AnnotationClassFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Configuration
public class UserManageTest {

	//@Test
	public void init(){
		
        
		
		/*WicketTester t=new WicketTester();
		t.startPage(UserManage.class);
		FormTester form=t.newFormTester("form");
		form.setValue("email", "yuanke@sohu.com");
		form.submit();*/
		//t.assertNoErrorMessage();
		//t.assertModelValue("email", "yuanke@sohu.com");
		//t.assertErrorMessages(expectedErrorMessages)
		
		
		
		
		
		
		
		
		
		// 1. setup dependencies and mock objects
				
				/*
				MockControl<UserDao> daoCtrl=MockControl.createControl(UserDao.class);
				UserDao dao=(UserDao) daoCtrl.getMock();
				User user=new User();
				user.setUserName("aaaaaaaaaa");
				dao.saveUser(user);
				System.out.println(user.getId()+"bbbbbbbbbbbbbbbbbbbbb");*/
				/*daoCtrl.expectAndReturn(dao.load(10), contact);
				dao.delete(10);
				
				daoCtrl.replay();
				
				// 2. setup mock injection environment
				ApplicationContextMock appctx=new ApplicationContextMock();
				appctx.putBean("contactDao", dao);
		*/
		
	}
	
	
}
