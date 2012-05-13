package com.lsqt.content.dao;

import java.io.Serializable;
import java.util.List;

import com.lsqt.content.model.User;

public interface UserDao {
	
	public boolean save(User user);

	public User update(User user);

	public boolean deleteById(Serializable id);

	public User findById(Serializable id) ;
	
	public List<User> findAll();
}
