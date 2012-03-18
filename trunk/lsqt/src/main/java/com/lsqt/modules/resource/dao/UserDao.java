package com.lsqt.modules.resource.dao;

import com.lsqt.modules.resource.model.User;

public interface UserDao {
	public boolean saveUser(User user);
	public User updateUser(User user);
	public boolean deleteUserById(String id);
	
}
