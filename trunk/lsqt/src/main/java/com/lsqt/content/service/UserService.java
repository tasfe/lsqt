package com.lsqt.content.service;




import com.lsqt.content.model.User;

public interface UserService {
	public boolean save(User user);
	public User update(User user);
	public boolean deleteById(String id);
	public User findById(String id) ;
	public boolean validate(String id,String pwd);
}
