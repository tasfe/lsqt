package com.lsqt.content.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.lsqt.content.model.User;
import com.lsqt.content.service.UserService;
import com.lsqt.modules.resource.model.ResourceType;

@Repository
public class UserDaoImpl extends AbstractHibernateDaoSupport<User> implements UserDao{


}
