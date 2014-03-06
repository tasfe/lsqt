package org.lsqt.content.web.controller;

import java.io.File;
import java.io.StringWriter;
import java.util.List;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.User;
import org.lsqt.content.web.filter.VelocityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
//import  org.aspectj.lang.NoAspectBoundException;
@Controller
public class UserController extends AbstractBaseController{
	
	@RequestMapping("/user/userAdd")
	public String userAdd(User user){
		
		
		
		Category c=new Category();
		c.setName("test");
		categoryService.save(c);
		
		System.out.println(userService);
		user.setEmail("袁明敏@sohu.com");
		
		userService.save(user);
		User myu=userService.findById(user.getId());
		
		return "userList";
	}
	
	@RequestMapping("/user/userInfo")
	public String userInfo(){
		User user=new User();
		
		user.setEmail("zs@sohu.com");
		userService.save(user);
		
		//根据不同用户显示模版显示用户详细
		String htmlDir=VelocityUtil.WEB_ROOT_ABSOLUTE_PATH+"/modules/html/";
		String fileName=VelocityUtil.buildHtmlFile(user, new File(htmlDir));
		if(fileName!=null){
			return "redirect:/modules/html/"+fileName;
		}
		return null;
	}
}
