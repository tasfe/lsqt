package com.hirisun.content.web.temptest;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;
import org.junit.Test;
import org.lsqt.content.model.User;



public class VelocityTest {
	
	//@Test
	public void testVelocity(){
		Velocity.init("/home/mm/Workspaces/sts_workspace/lsqt/src/main/resources/velocity.properties");
		VelocityContext content=new VelocityContext();
		content.put("who", "袁明敏");
		content.put("content", "你好!");
		
		//list迭代写法
		List<String> list=new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");
		list.add("钱七"); 
		content.put("names", list);
		
		//map迭代写法
		Map<String,String> map=new HashMap<String,String>();
		map.put("张三", "1984-10-20");
		map.put("李四", "1989-04-01");
		map.put("王五", "1991-07-23");
		map.put("赵六", "1978-03-05");
		map.put("钱七", "1953-09-02");
		content.put("userMap", map);
		
		//日期格式化
		SimpleDateFormat ds=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		content.put("myCurrTime", ds.format(new Date()));
		content.put("myCurrTime2", new Date());
		
		content.put("myDateTool", new DateTool());
		
		
		//对象方法调用
		content.put("human", new User());
		
		
		//velocity空值引用将会按原样输出,加个"!"符号就为空了
		content.put("title", null);
		
		Template tmp=Velocity.getTemplate("hello.vm");
		StringWriter sw=new StringWriter();
		tmp.merge(content, sw);
		sw.flush();
		System.out.println(sw.toString());
	}
}

