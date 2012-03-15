package com.lsqt.modules.resource.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsqt.modules.resource.dao.StudentDao;

@Service("studentService")
public class StudentServiceImpl implements StudentService{
	private StudentDao studentDao;

	@Resource()
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	@Transactional(readOnly=true)
	public void test() {
		studentDao.test();
	}
	
	
}
