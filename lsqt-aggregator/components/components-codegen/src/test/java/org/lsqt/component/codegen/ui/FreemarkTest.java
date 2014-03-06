package org.lsqt.component.codegen.ui;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkTest {
	
	@Test
	public void testFreemark() throws IOException, TemplateException{
		User user=new User();
		Map root=new HashMap();
		user.setName("jack");
		user.setId(1L);
		user.setBirthday(new Date());
		user.setAddress(null);
		root.put("user",user);
		
		
		User user2=new User();
		user2.setName("lilei");
		user2.setId(2L);
		user2.setBirthday(new Date());
		user2.setAddress("北京");
		
		User user3=new User();
		user3.setName("xiaoming");
		user3.setId(3L);
		user3.setBirthday(new Date());
		user3.setAddress("上海");
		
		List<User> list=new ArrayList<User>();
		list.add(user);
		list.add(user2);
		list.add(user3);
		root.put("userList",list);
		
		
		Configuration config=new Configuration();
		config.setDirectoryForTemplateLoading(new File(User.TMPL_FOLDER)); 
		config.setObjectWrapper(new DefaultObjectWrapper());
		Template template=config.getTemplate("user.ftl");
		Writer out=new OutputStreamWriter(System.out);
		template.process(root , out);
		out.flush();
		out.close();
	}
}
