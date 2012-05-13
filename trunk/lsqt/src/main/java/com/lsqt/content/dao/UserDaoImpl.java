package com.lsqt.content.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.content.model.ResourceType;
import com.lsqt.content.model.User;
import com.lsqt.content.service.UserService;

@Repository
public class UserDaoImpl extends AbstractHibernateDaoSupport<User> implements UserDao{


}
