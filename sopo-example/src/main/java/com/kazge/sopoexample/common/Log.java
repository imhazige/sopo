package com.kazge.sopoexample.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log
{
	private static Logger log = LoggerFactory.getLogger(Log.class.getPackage().getName());

	public static void debug(Object obj)
	{
		String msg = null != obj ? obj.toString() : "null";
		if (log.isDebugEnabled())
		{
			log.debug(msg);
		}
	}

	public static void debug(String format, Object... args)
	{
		if (log.isDebugEnabled())
		{
			log.debug(String.format(format, args));
		}
	}

	public static void info(Object obj)
	{
		String msg = null != obj ? obj.toString() : "null";
		if (log.isInfoEnabled())
		{
			log.info(msg);
		}
	}

	public static void info(String format, Object... args)
	{
		if (log.isInfoEnabled())
		{
			log.info(String.format(format, args));
		}
	}

	public static void warn(Object obj)
	{
		String msg = null != obj ? obj.toString() : "null";
		if (log.isWarnEnabled())
		{
			log.warn(msg);
		}
	}

	public static void warn(String format, Object... args)
	{
		if (log.isWarnEnabled())
		{
			log.warn(String.format(format, args));
		}
	}

	public static void error(Object obj)
	{
		String msg = null != obj ? obj.toString() : "null";
		if (log.isErrorEnabled())
		{
			log.error(msg);
		}
	}

	public static void error(Throwable ex)
	{
		if (log.isErrorEnabled())
		{
			log.error(ex.toString(), ex);
		}
	}

	public static void error(String format, Object... args)
	{
		if (log.isErrorEnabled())
		{
			log.error(String.format(format, args));
		}
	}
	
	public static void error(Throwable ex,String format, Object... args)
	{
		if (log.isErrorEnabled())
		{
			log.error(String.format(format, args),ex);
		}
	}

}
