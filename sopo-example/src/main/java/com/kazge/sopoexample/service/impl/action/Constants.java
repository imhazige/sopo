package com.kazge.sopoexample.service.impl.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constants
{
	public static String REGEXP_EMAIL = null; 
	public static String REGEXP_DATE = null;
	
	static {
		InputStream inStream = null;
		try
		{
			Properties props = new Properties();
			inStream = Constants.class.getResourceAsStream("RegExp.xml");
			props.loadFromXML(inStream);
			REGEXP_EMAIL = props.getProperty("email");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			try
			{
				if (null != inStream){
					inStream.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
