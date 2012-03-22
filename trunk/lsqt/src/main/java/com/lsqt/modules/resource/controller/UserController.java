package com.lsqt.modules.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsqt.modules.AbstractBaseController;
import com.lsqt.modules.resource.model.User;
//import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
//import  org.aspectj.lang.NoAspectBoundException;
@Controller
@RequestMapping("user.sp")
public class UserController extends AbstractBaseController{
	
	@RequestMapping(params = "method=userAdd")
	public String userAdd(User user){
		System.out.println(userService);
		
		user.setUserName("格三");
		userService.saveUser(user);
		User myu=userService.findById("ff8081813639b117013639b11bb60001");
		
		System.out.println("test........."+myu.getEmail());
		return "userList";
	}
}
