package com.kazge.sopo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StringUtils extends org.apache.commons.lang.StringUtils
{
	public static String exceptionStatck2String(Throwable e)
	{
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		
		return sw.toString();
	}
	
	public static String upperFirst(String str)
	{
		if (null == str)
		{
			return null;
		}
		
		if (1 == str.length())
		{
			return str.toUpperCase();
		}
		
		return (str.charAt(0)+"").toUpperCase() + str.substring(1);
	}
}
