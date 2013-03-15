package org.lsqt.content.web.wicket.util;

import javax.servlet.ServletContext;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.file.File;

/**
 * 
 * @author 袁明敏
 *
 */
public class WebUtil{
	
	/**
	 * 获取web应用的布署文件夹根路径.
	 * @return 
	 */
	public static String getWebRoot(){
		ServletContext context = ((WebApplication) Session.get().getApplication()).getServletContext(); 
		String path=context.getRealPath(File.separator);
		return path;
	}
}
