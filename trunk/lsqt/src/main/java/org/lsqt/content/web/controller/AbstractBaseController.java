package org.lsqt.content.web.controller;

import javax.annotation.Resource;

import org.lsqt.content.service.UserService;
import org.springframework.stereotype.Controller;


@Controller
public class AbstractBaseController {
	protected UserService userService;

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
