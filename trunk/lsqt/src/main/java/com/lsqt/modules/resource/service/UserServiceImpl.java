package com.lsqt.modules.resource.service;

import java.util.List;

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
	public boolean saveUser(User user){
		return this.userDao.save(user);
	}
	@Transactional(readOnly=false)
	public User updateUser(User user){
		return this.userDao.update(user);
	}
	@Transactional(readOnly=false)
	public boolean deleteUserById(String id){
		return this.userDao.deleteById(id);
	}
	
	public User findById(String id) {
		return userDao.findById(id);
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}
}
