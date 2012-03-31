package com.lsqt.modules.resource.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.modules.resource.model.ResourceType;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.service.UserService;

@Repository
public class UserDaoImpl extends AbstractHibernateDaoSupport<User> implements UserDao{
	@Override
	public boolean saveUser(User user) {
		return super.save(user);
	}

	@Override
	public boolean deleteUserById(String id) {
		return super.deleteById(id);
	}

	@Override
	public User updateUser(User user) {
		return super.update(user);
	}
	
	public User findById(String id){
		return super.findById(id);
	}
}
