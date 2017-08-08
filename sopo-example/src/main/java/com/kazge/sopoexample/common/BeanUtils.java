package com.kazge.sopoexample.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BeanUtils
{
	public static void setNestedProperty(Object bean, String name, Object value) throws NoSuchFieldException,
		SecurityException, IllegalArgumentException, IllegalAccessException
	{
		Field fd = findNestedProperty(bean, name);

		if (null == fd)
		{
			throw new NoSuchFieldException("no such field through all the hierarchy :" + name);
		}

		fd.setAccessible(true);
		fd.set(bean, value);
	}

	public static Object getNestedProperty(Object bean, String name) throws IllegalArgumentException,
		IllegalAccessException, NoSuchFieldException
	{
		Field fd = findNestedProperty(bean, name);

		if (null == fd)
		{
			throw new NoSuchFieldException("no such field through all the hierarchy :" + name);
		}
		fd.setAccessible(true);

		return fd.get(bean);
	}

	@SuppressWarnings("unchecked")
	public static Field findNestedProperty(Object bean, String name)
	{
		Field fd = null;
		Class clazz = bean.getClass();

		while (null != clazz && null == fd)
		{
			try
			{
				fd = clazz.getDeclaredField(name);
			}
			catch (NoSuchFieldException ex)
			{
				clazz = clazz.getSuperclass();
			}
		}

		return fd;
	}

	public static Object readObject(String string)
	{
		ByteArrayInputStream bins = null;
		ObjectInputStream ins = null;

		try
		{
			byte[] buff = new BASE64Decoder().decodeBuffer(string);
			bins = new ByteArrayInputStream(buff);
			ins = new ObjectInputStream(bins);

			ins.close();

			return ins.readObject();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if (null != bins)
				{
					bins.close();
				}
			}
			catch (IOException ex)
			{
			}
			try
			{
				if (null != ins)
				{
					ins.close();
				}
			}
			catch (IOException ex)
			{
			}
		}
	}

	public static String object2String(Serializable obj)
	{
		ObjectOutputStream out = null;
		ByteArrayOutputStream byteStream = null;
		try
		{
			byteStream = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteStream);
			out.writeObject(obj);
			byte[] buff = byteStream.toByteArray();

			BASE64Encoder encoder = new BASE64Encoder();
			String objstr = encoder.encode(buff);

			return objstr;
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				if (null != out)
				{
					out.close();
				}
			}
			catch (IOException e)
			{
			}
			try
			{
				if (null != byteStream)
				{
					byteStream.close();
				}
			}
			catch (IOException e)
			{
			}
		}
	}
}
