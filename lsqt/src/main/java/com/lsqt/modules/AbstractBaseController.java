package com.lsqt.modules;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lsqt.modules.resource.service.UserService;

@Controller
public class AbstractBaseController {
	protected UserService userService;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
