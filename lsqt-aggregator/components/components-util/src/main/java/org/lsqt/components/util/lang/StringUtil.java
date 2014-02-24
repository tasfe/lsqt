package org.lsqt.components.util.lang;


public class StringUtil {
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str!=null){
			return str.trim().length()==0;
		}else{
			return true;
		}
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

}
