package com.lsqt.content.dao;

import org.lsqt.components.dao.hibernate.AbstractHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.lsqt.content.model.User;



@Repository
public class UserDaoImpl extends AbstractHibernateDaoSupport<User> implements UserDao{
	
	public boolean validate(String id, String pwd) {
		String hql="select count(*) from User u where u.userId =? and userPwd=?";
		Object cnt=super.uniqueResultByHql(hql, new Object[]{id,pwd});
		return Integer.valueOf(cnt==null ? "0":cnt.toString() )>0;
	}

}
