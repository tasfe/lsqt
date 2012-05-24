package org.lsqt.content.web.controller;

import javax.annotation.Resource;

import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.UserService;
import org.springframework.stereotype.Controller;


@Controller
public class AbstractBaseController {
	protected UserService userService;
	protected CategoryService categoryService;
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	
}
