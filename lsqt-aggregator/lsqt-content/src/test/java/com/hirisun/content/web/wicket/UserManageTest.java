package com.hirisun.content.web.wicket;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;
import org.lsqt.content.web.wicket.content.NewsAddPage;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserManageTest {

	@Test
	public void init(){
		
        
		
		WicketTester t=new WicketTester();
		t.startPage(NewsAddPage.class);
		FormTester form=t.newFormTester("form");
		form.setValue("txtCategories", "yuanke@sohu.com");
		form.submit();
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
