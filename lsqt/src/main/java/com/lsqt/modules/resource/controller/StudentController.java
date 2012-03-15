package com.lsqt.modules.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsqt.modules.AbstractBaseController;
import com.lsqt.modules.resource.model.Student;

@Controller
@RequestMapping("student.sp")
public class StudentController extends AbstractBaseController{
	
	@RequestMapping(params = "method=studentAdd")
	public String studentAdd(Student student){
		studentService.test();
		return "studentList";
	}
}
