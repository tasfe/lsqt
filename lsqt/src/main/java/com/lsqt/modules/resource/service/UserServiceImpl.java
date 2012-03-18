package com.lsqt.modules.resource.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsqt.modules.resource.dao.UserDao;

@Service
public class UserServiceImpl implements UserService{
	private UserDao userDao;

	@Resource()
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	
}
