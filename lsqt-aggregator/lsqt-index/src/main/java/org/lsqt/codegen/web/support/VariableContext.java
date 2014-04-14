package org.lsqt.codegen.web.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取系统环境变量
 * @author Sky
 *
 */
public class VariableContext {
	public static String PATH_CTX="CTX_PATH";
	public static String PATH_RES="RES_PATH";
	public static Map<String,String> VARIABLE=new HashMap<String,String>();
	
	public static String getCtxPath(){
		return VARIABLE.get(PATH_CTX)==null ? "":VARIABLE.get(PATH_CTX);
	}
	
	public static String getResPath(){
		return VARIABLE.get(PATH_RES)==null ? "":VARIABLE.get(PATH_RES);
	}
}
