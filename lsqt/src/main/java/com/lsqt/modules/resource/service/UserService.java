package com.lsqt.modules.resource.service;




import com.lsqt.modules.resource.model.User;

public interface UserService {
	public boolean save(User user);
	public User update(User user);
	public boolean deleteById(String id);
	public User findById(String id) ;
}
