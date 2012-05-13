package com.lsqt.content.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lsqt.content.service.UserService;

@Controller
public class AbstractBaseController {
	protected UserService userService;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
