package com.lsqt.modules;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lsqt.modules.resource.service.StudentService;

@Controller
public class AbstractBaseController {
	protected StudentService studentService;

	@Resource
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
}
