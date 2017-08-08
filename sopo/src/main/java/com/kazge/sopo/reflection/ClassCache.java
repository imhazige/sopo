package com.kazge.sopo.reflection;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.kazge.sopo.Request;



public class ClassCache
{
	private Map<String,Class<?>> cache;
	
	private static final String KEY = ClassCache.class.getName();
		
	private ClassCache()
	{
		
	}
	
	public static ClassCache instance()
	{
		ServletContext context = Request.getCurrentInstance().getSession().getServletContext();
				
		return instance(context);
	}
	
	public static ClassCache instance(ServletContext context)
	{
		if (null == context.getAttribute(KEY))
		{
			context.setAttribute(KEY, new ClassCache());	
		}
		
		return (ClassCache)context.getAttribute(KEY);
	}
	
	public void push(String className,Class<?> value)
	{
		getCache().put(className.toLowerCase(), value);
	}
		
	public Class<?> forName(String className) throws ClassNotFoundException
	{
		String kclassName = className.toLowerCase();
		if (getCache().containsKey(kclassName))
		{
			return getCache().get(kclassName);
		}
		
		Class<?> clazz = Class.forName(className);
		push(kclassName, clazz);	
		
		return clazz;
	}

	private Map<String, Class<?>> getCache()
	{
		if (null == cache)
		{
			cache = new HashMap<String, Class<?>>();
		}
		
		return cache;
	}	
}
