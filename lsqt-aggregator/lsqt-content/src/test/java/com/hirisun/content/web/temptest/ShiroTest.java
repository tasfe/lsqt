package com.hirisun.content.web.temptest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest {
	   private static Logger logger = LoggerFactory.getLogger(ShiroTest.class);
	   private static String INI_PATH="/home/mm/Workspaces/sts_workspace/lsqt-aggregator/lsqt-content/src/main/resources/auth.ini";
	   
	    public static void main(String[] args) {
	        // Using the IniSecurityManagerFactory, which will use the an INI file
	        // as the security file.
	        Factory<org.apache.shiro.mgt.SecurityManager> factory =   new IniSecurityManagerFactory(INI_PATH);

	        // Setting up the SecurityManager...
	        org.apache.shiro.mgt.SecurityManager securityManager  = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);

	        Subject user = SecurityUtils.getSubject();

	        UsernamePasswordToken token = new UsernamePasswordToken("bjangles", "dance");
	        user.login(token);
	        
	        logger.info("User is authenticated:  " + user.isAuthenticated());
	        
	        LdapTest();
	    }
	    
	    public static void LdapTest(){
	    	// Using the IniSecurityManagerFactory, which will use the an INI file
	        // as the security file.
	        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(INI_PATH);

	        // Setting up the SecurityManager...
	        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	        SecurityUtils.setSecurityManager(securityManager);

	        Subject user = SecurityUtils.getSubject();

	        logger.info("User is authenticated:  " + user.isAuthenticated());

	        UsernamePasswordToken token = 
	        new UsernamePasswordToken("cn=Cornelius Buckley,ou=people,o=sevenSeas", "argh");

	        user.login(token);

	        logger.info("User is authenticated:  " + user.isAuthenticated());

	    }
}
