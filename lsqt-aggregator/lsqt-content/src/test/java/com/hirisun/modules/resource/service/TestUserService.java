package com.hirisun.modules.resource.service;

import java.util.Date;

import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Assert;
import org.junit.Test;
import org.lsqt.content.model.User;
import org.lsqt.content.service.UserService;

import com.hirisun.AbstractTest;
import com.opensymphony.oscache.hibernate.OSCache;
import com.opensymphony.oscache.hibernate.OSCacheProvider;
 
public class TestUserService extends AbstractTest{
	private UserService userService=getBean(UserService.class);
	@Test
	public void testCRUDUser(){
		
		User user=new User();
		user.setEmail("yuanke52014中国人@sohu.com");
		user.setUserId("yuanke");
		user.setUserPwd("admin");
		
		
		userService.save(user);
		Assert.assertNotNull(user.getId());
		
		
		
		user.setEmail("keke@sohu.com");
		
		user.setUserId("administrator");
		user.setUserPwd("administrator");
		userService.update(user);
		
		
		String pk=user.getId();
		for(int i=0;i<50;i++){
			//userService.findById(i+"");
			userService.findById(pk);
			/*if(i==49){
				userService.findById(pk);
			}*/
			
		}
		
		boolean isOk=userService.deleteById(pk);
		
		
		Assert.assertTrue(isOk);
	}
}
