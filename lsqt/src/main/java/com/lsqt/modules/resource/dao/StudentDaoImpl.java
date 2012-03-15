package com.lsqt.modules.resource.dao;

import org.springframework.stereotype.Repository;

import com.lsqt.core.dao.hibernate.impl.AbstractHibernateDaoSupport;
import com.lsqt.modules.resource.model.Student;
@Repository("studentDao")
public class StudentDaoImpl extends AbstractHibernateDaoSupport<Student> implements StudentDao{
	public StudentDaoImpl(){}
	
	public void test(){
		this.executeSqlQuery("select * from test_student");
	}
}
