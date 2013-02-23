package org.lsqt.content.cache;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
public class MethodCacheAfterAdvice  implements AfterReturningAdvice, InitializingBean
{
	private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);
	private Cache cache;
	public void setCache(Cache cache)
	{
		this.cache = cache;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");
	}
	
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable
	{
		String className = target.getClass().getName();
		List list = cache.getKeys();
		for (int i = 0; i < list.size(); i++)
		{
			String cacheKey = String.valueOf(list.get(i));
			if (cacheKey.startsWith(className))
			{
				cache.remove(cacheKey);
				logger.debug("remove cache " + cacheKey);
			}
		}

	}
	
	
}
