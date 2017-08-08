package com.kazge.sopo.util;

import com.kazge.sopo.util.ClassUtils;

import junit.framework.Test;
import junit.framework.TestCase;

public class ClassUtilsTest extends TestCase
{
	public void testMany()
	{
		System.out.println(ClassUtilsTest.class.getSigners());
		System.out.println(ClassUtilsTest.class.getSuperclass());
		Class [] clazzzs = TestCase.class.getInterfaces();
		for (int i = 0 ; i < clazzzs.length ; i++)
		{
			System.out.println(clazzzs[i]);	
		}
				
	}
	
	public void testIsSubclassOf()
	{
		assertTrue(ClassUtils.isSubclassOf(ClassUtilsTest.class, TestCase.class));
		assertTrue(ClassUtils.isSubclassOf(ClassUtilsTest.class, Test.class));
		assertTrue(ClassUtils.isSubclassOf(ClassUtilsTest.class, Object.class));
		assertFalse(ClassUtils.isSubclassOf(ClassUtilsTest.class, String.class));
	}
}
