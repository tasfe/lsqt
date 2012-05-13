package com.lsqt.modules.resource.controller;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lsqt.modules.AbstractBaseController;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.web.console.VelocityUtil;
//import org.springframework.transaction.aspectj.AnnotationTransactionAspect;
//import  org.aspectj.lang.NoAspectBoundException;
@Controller
@RequestMapping("user.sp")
public class UserController extends AbstractBaseController{
	
	@RequestMapping(params = "method=userAdd")
	public String userAdd(User user){
		System.out.println(userService);
		
		
		userService.save(user);
		User myu=userService.findById("ff8081813639b117013639b11bb60001");
		
		System.out.println("test........."+myu.getEmail());
		return "userList";
	}
	
	@RequestMapping(params = "method=userInfo")
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
