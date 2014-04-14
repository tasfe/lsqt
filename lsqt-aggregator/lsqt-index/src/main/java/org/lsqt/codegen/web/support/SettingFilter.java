package org.lsqt.codegen.web.support;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上下文环境变量的设置
 * @author Sky
 *
 */
public class SettingFilter implements Filter {
	
	
	public void init(FilterConfig config) throws ServletException {
		String ctx=config.getServletContext().getContextPath();
		VariableContext.VARIABLE.put(VariableContext.PATH_CTX, ctx);
		System.out.println("初使化上下文路径："+ctx);
		
		String res=config.getInitParameter(VariableContext.PATH_RES);
		res="<script type='text/javascript' src='"+res+"'></script>";
		System.out.println("初使化资源路径："+res);
		
		VariableContext.VARIABLE.put(VariableContext.PATH_RES, res);
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		
		if(req.getSession().getAttribute("ctx")==null){
			req.getSession().setAttribute("ctx", VariableContext.getCtxPath());
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
		System.getProperties().clear();
	}

}
