package com.lsqt.modules.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsqt.modules.AbstractBaseController;
import com.lsqt.modules.resource.model.User;

@Controller
@RequestMapping("user.sp")
public class UserController extends AbstractBaseController{
	
	@RequestMapping(params = "method=userAdd")
	public String userAdd(User user){
		
		return "userList";
	}
}
