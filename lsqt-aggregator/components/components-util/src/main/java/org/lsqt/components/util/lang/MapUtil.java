package org.lsqt.components.util.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MapUtil {
	
	public static List toKeyList(Map map){
		List list=new ArrayList();
		Set<Entry> entry=map.entrySet();
		for(Entry e:entry){
			list.add(e.getKey());
		}
		return list;
	}
	
	public static List toValueList(Map map){
		List list=new ArrayList();
		Set<Entry> entry=map.entrySet();
		for(Entry e:entry){
			list.add(e.getValue());
		}
		return list;
	}
}
