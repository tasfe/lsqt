package com.lsqt.modules.resource.web.console;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.lsqt.content.model.User;

public final class VelocityUtil {
	public static String WEB_ROOT_ABSOLUTE_PATH;
	
	public static String buildHtmlFile(User user , File saveDir){
		try {
				if (!saveDir.exists()) {
					saveDir.mkdirs();
				}
			   VelocityContext context = new VelocityContext();
			   context.put("user", user);
			   Template template = Velocity.getTemplate("userInfo.vm");
			   FileOutputStream outStream = new FileOutputStream(new File(saveDir,user.getId()+".html"));
			   OutputStreamWriter writer =  new OutputStreamWriter(outStream,"UTF-8");
			   BufferedWriter sw = new BufferedWriter(writer);
			   template.merge(context, sw);
			   sw.flush();
			   sw.close();
			   outStream.close();
			   
			   return user.getId()+".html";
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			return null;
		}
}
