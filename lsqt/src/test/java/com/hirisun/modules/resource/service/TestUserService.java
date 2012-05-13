package com.hirisun.modules.resource.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.hirisun.AbstractTest;
import com.lsqt.content.model.User;
import com.lsqt.content.service.UserService;

public class TestUserService extends AbstractTest{
	@Test
	public void testCRUDUser(){
		User user=new User();
		
		user.setEmail("yuanke52014@sohu.com");
		
		user.setUserId("yuanke");
		
		user.setUserPwd("admin");
		UserService userService=getBean(UserService.class);
		
		userService.save(user);
		Assert.assertNotNull(user.getId());
		
		
		
		user.setEmail("keke@sohu.com");
		
		user.setUserId("administrator");
		user.setUserPwd("administrator");
		userService.update(user);
		
		
		String pk=user.getId();
		boolean isOk=userService.deleteById(pk);
		Assert.assertTrue(isOk);
		
	}
}
