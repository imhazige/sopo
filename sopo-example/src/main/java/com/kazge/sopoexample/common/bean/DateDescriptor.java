package com.kazge.sopoexample.common.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kazge.sopoexample.common.BeanUtils;
import com.kazge.sopoexample.common.Log;


public class DateDescriptor extends BasePropertyDescriptor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1953857661388055L;

	private String dateformat;
	
	private SimpleDateFormat dateformater;

	public DateDescriptor(String argDateFormat)
	{
		 setDateformat(argDateFormat);
	}	
	
	@Override
	public String display(Object bean)
	{
		try
		{
			Date date = (Date)BeanUtils.getNestedProperty(bean, getName());
			return null!= date?dateformater.format(date):getDefaultValue();
		}
		catch (Exception e)
		{
			Log.error(e);
		}
		
		return getDefaultValue();
	}

	public String getDateformat()
	{
		return dateformat;
	}

	public void setDateformat(String argDateformat)
	{
		this.dateformat = argDateformat;
		dateformater = new SimpleDateFormat(dateformat);
	}
	
	@Override
	public Date parse(String value)
	{		
		try
		{
			Date date = dateformater.parse(value);
			
			return date;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
