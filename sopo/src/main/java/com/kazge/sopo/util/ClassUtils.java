package com.kazge.sopo.util;


public class ClassUtils
{
	public static Class<?> forNameOfSubclass(String className, Class<?> argClazz)
	{
		try
		{
			Class<?> clazz = Class.forName(className);
			if (isSubclassOf(clazz,argClazz))
			{
				return clazz;
			}
		}
		catch (ClassNotFoundException e)
		{
			
		}
		return null;
	}

	public static boolean isSubclassOf(Class<?> argClazz, Class<?> argClazzParent)
	{
		Class<?> parent = argClazz;
		while (null != parent)
		{
			if (argClazzParent.equals(parent))
			{
				return true;
			}
			Class<?> [] clazzzs = parent.getInterfaces();
			for (int i = 0 ;null != clazzzs && i < clazzzs.length ; i++)
			{
				if (argClazzParent.equals(clazzzs[i]))
				{
					return true;
				}
			}			
			parent = parent.getSuperclass();
		}

		return false;
	}
}
