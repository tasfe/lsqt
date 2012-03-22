package com.lsqt.modules.resource.service;


import org.springframework.stereotype.Service;

import com.lsqt.modules.resource.model.User;
@Service
public interface UserService {
	public boolean saveUser(User user);
	public User updateUser(User user);
	public boolean deleteUserById(String id);
}
