package com.hirisun.content.service;

import org.junit.Assert;
import org.junit.Test;
import org.lsqt.content.model.User;
import org.lsqt.content.service.UserService;
import org.lsqt.util.db.DataBaseUtil;
import org.hibernate.cache.EhCacheProvider;
import com.hirisun.AbstractTest;

public class UserServiceTest extends AbstractTest{
	private UserService userService=getBean(UserService.class);
	@Test
	public void testCRUDUser(){
		
		
		System.out.println(Math.pow(2, 69)+"============");
		User user=new User();
		user.setEmail("yuanke52014中国人@sohu.com");
		user.setUserId("yuanke");
		user.setUserPwd("admin");
		
		
		userService.save(user);
		Assert.assertNotNull(user.getId());
		
		user.setEmail("keke@sohu.com");
		
		user.setUserId("administrator");
		user.setUserPwd("administrator");
		user=userService.update(user);
		
		
		String pk=user.getId();
		for(int i=0;i<10;i++){
			userService.findById(pk);
		}
		
		boolean isOk=userService.deleteById(pk);
		
		Assert.assertTrue(isOk);
	}
}
