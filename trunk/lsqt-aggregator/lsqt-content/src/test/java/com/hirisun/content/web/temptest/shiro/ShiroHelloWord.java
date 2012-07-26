package com.hirisun.content.web.temptest.shiro;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.util.Factory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
public class ShiroHelloWord {
	private static final Logger LOGGER = Logger.getLogger(ShiroHelloWord.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 // Factory<org.apache.shiro.mgt.SecurityManager> factory =   new IniSecurityManagerFactory("classpath:shiro.ini");
		Factory<SecurityManager> factory= new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager manager=factory.getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject currentUser= SecurityUtils.getSubject();
		Session session=currentUser.getSession();
		session.setAttribute("somekey", "aValue");
		
		
		if(!  currentUser.isAuthenticated()){
			UsernamePasswordToken token=new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);
			currentUser.login(token);
		}
		
		if(currentUser.hasRole("schwartz")){
			LOGGER.info("My the schwartz be with you");
		}else{
			LOGGER.info("hello , mero mortal");
		}
		
		if(currentUser.isPermitted("winnebago:drive:eagle5")){
			LOGGER.info("you're permitted to 'dirver' winnebago:drive:eagle5  with   eagle5");
		}else{
			LOGGER.info("sorry....");
		}
		
		currentUser.logout();
	}

}
