package com.lsqt.modules.resource.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsqt.modules.resource.dao.UserDao;
import com.lsqt.modules.resource.model.User;
@Service("userService")
public class UserServiceImpl implements UserService{
	private UserDao userDao;

	@Resource()
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Transactional(readOnly=false)
	public boolean saveUser(User user){
		return this.userDao.saveUser(user);
	}
	@Transactional(readOnly=false)
	public User updateUser(User user){
		return this.userDao.updateUser(user);
	}
	@Transactional(readOnly=false)
	public boolean deleteUserById(String id){
		return this.userDao.deleteUserById(id);
	}
	@Transactional(readOnly=true)
	public User findById(String id) {
		return userDao.findById(id);
	}
}
