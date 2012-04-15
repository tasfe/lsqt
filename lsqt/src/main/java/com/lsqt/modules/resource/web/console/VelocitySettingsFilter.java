package com.lsqt.modules.resource.web.console;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.PropertyConfigurator;
import org.apache.velocity.app.Velocity;

public class VelocitySettingsFilter implements Filter {
	
	/**
	 * velocity.property属性配置迁移至该init实现
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		Properties prop = new Properties();
		prop.put("runtime.log", config.getServletContext().getRealPath("/WEB-INF/classes/log/velocity.log"));  //注意,指定为文件(非目录)
		prop.put("file.resource.loader.path", config.getServletContext().getRealPath("/WEB-INF/classes/vm"));
		prop.put("input.encoding", "UTF-8");
		prop.put("output.encoding", "UTF-8");
		Velocity.init(prop);
		
		
		Properties properties = new Properties();
		
		try {
			InputStream input=VelocitySettingsFilter.class.getResourceAsStream("/log4j.properties");
			properties.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
          
		String logFile = properties.getProperty("log4j.appender.logfile.File");// 设置路径
		File logDir=new File(config.getServletContext().getRealPath("/WEB-INF/classes/log/"));
		if(!logDir.exists()){
			logDir.mkdir();
		}
		properties.setProperty("log4j.appender.logfile.File", logDir.getAbsolutePath()+"/"+logFile);
		PropertyConfigurator.configure(properties);// 装入log4j配置信息
		
		//System.setProperty ("classes", config.getServletContext().getRealPath("/WEB-INF/classes"));
		
		VelocityUtil.WEB_ROOT_ABSOLUTE_PATH=config.getServletContext().getRealPath("/");
		System.out.println(VelocityUtil.WEB_ROOT_ABSOLUTE_PATH);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		

	}

}
