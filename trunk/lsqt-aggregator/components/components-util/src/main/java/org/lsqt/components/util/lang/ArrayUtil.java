package org.lsqt.components.util.lang;

import java.util.List;

public class ArrayUtil {
	
	public static String join(List<String> list,String separator){
		StringBuffer temp=new StringBuffer();
		for(int i=0;i<list.size();i++){
			temp.append(list.get(i));
			if(i!=list.size()-1){
				temp.append(separator);
			}
		}
		return temp.toString();
	}
}
