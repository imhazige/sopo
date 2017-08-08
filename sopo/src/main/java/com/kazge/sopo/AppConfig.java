package com.kazge.sopo;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

public abstract class AppConfig
{
	private Properties properties;
	
	public void init(ServletContext context)
	{
		
	}
	
	public abstract Navigate getNavigate();
	
	public Map<String , String> registerLibrary()
	{
		return null;
	}
		
	public Charset getTemplateCharset()
	{
		return Charset.forName("utf-8");
	}	
		
	public boolean isDevelopMode()
	{
		return false;
	}
	
	public void put(String key,Object value)
	{
		getProperties().put(key, value);
	}
	
	public Object get(String key)
	{
		return getProperties().get(key);
	}

	private Properties getProperties()
	{
		if (null == properties)
		{
			properties = new Properties();
		}
		
		return properties;
	}
	
	public String getRequestEncoding()
	{
		return null;
	}
	
	public String getResponseEncoding()
	{
		return null;
	}
}

