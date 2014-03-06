package org.lsqt.component.codegen.ui;

import java.util.Date;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class VelocityTest {
	
	@Test
	public void testVelocity(){
		User user=new User();
		user.setName("jack");
		user.setId(1L);
		user.setBirthday(new Date());
		user.setAddress(null);
		
		
		
		Properties prop = new Properties();
		prop.put("runtime.log", User.TMPL_FOLDER+"/velocity.log");  //注意,指定为文件(非目录)
		prop.put("file.resource.loader.path", User.TMPL_FOLDER);
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
		Velocity.init(prop);
		
		VelocityContext context = new VelocityContext();
		context.put("user", user);
		Template template = Velocity.getTemplate("userInfo.vm");
	}
}
