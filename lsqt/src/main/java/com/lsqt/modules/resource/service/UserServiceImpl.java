package com.lsqt.modules.resource.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsqt.modules.resource.dao.UserDao;
import com.lsqt.modules.resource.model.User;
@Service("userService")
public class UserServiceImpl implements UserService{
	private UserDao userDao;

	@Resource
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional(readOnly=false)
	public boolean save(User user) {
		return this.userDao.save(user);
	}

	@Transactional(readOnly=false)
	public User update(User user) {
		return this.userDao.update(user);
	}

	@Transactional(readOnly=false)
	public boolean deleteById(String id) {
		return this.userDao.deleteById(id);
	}

	public User findById(String id) {
		return this.userDao.findById(id);
	}
	

}
