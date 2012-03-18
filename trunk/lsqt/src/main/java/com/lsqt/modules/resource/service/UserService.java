package com.lsqt.modules.resource.service;

import com.lsqt.modules.resource.model.User;


public interface UserService {
	public boolean saveUser(User user);
	public User updateUser(User user);
	public boolean deleteUserById(String id);
}
