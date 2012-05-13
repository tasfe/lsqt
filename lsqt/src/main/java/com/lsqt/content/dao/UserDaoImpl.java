package com.lsqt.content.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.content.model.ResourceType;
import com.lsqt.content.model.User;
import com.lsqt.content.service.UserService;

@Repository
public class UserDaoImpl extends AbstractHibernateDaoSupport<User> implements UserDao{
	
	public boolean validate(String id, String pwd) {
		String hql="select count(*) from User u where u.userId =? and userPwd=?";
		Object cnt=super.uniqueResultByHql(hql, new Object[]{id,pwd});
		
		return Integer.valueOf(cnt==null ? "0":cnt.toString() )>0;
	}

}
