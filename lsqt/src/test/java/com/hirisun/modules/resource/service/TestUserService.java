package com.hirisun.modules.resource.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.hirisun.AbstractTest;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.service.UserService;

public class TestUserService extends AbstractTest{
	@Test
	public void testCRUDUser(){
		User user=new User();
		user.setBirthday(new Date());
		user.setDescript("这是一个描述");
		user.setEmail("yuanke52014@sohu.com");
		user.setSex(0);
		user.setUserId("yuanke");
		user.setUserName("admin");
		user.setUserPwd("admin");
		UserService userService=getBean(UserService.class);
		
		userService.save(user);
		Assert.assertNotNull(user.getId());
		
		
		user.setBirthday(new Date());
		user.setDescript("描述");
		user.setEmail("keke@sohu.com");
		user.setSex(1);
		user.setUserId("administrator");
		user.setUserName("yuanmingmin");
		user.setUserPwd("administrator");
		userService.update(user);
		
		
		String pk=user.getId();
		boolean isOk=userService.deleteById(pk);
		Assert.assertTrue(isOk);
		
	}
}
