package org.lsqt.util.db;

import java.lang.reflect.AnnotatedElement;

import org.junit.Test;

//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;


/**
 * 
 * @author mm
 *
 */
public final class DataBaseUtilTest {
	@Test(timeout=3)
	public void testConnection(){
		
		AnnotatedElement e=null;
		 // 使用默认配置文件创建CacheManager
	   // CacheManager manager = CacheManager.create();
	    // 通过manager可以生成指定名称的Cache对象
	    //Cache cache = cache = manager.getCache("demoCache");
	    // 使用manager移除指定名称的Cache对象
	   // manager.removeCache("demoCache");
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}

