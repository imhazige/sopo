package com.kazge.sopo.reflection;

import java.util.Collection;

import com.kazge.sopo.reflection.ClassNameLocator;

import junit.framework.TestCase;

public class ClassNameLocatorTest extends TestCase
{
	public void testList()
	{
		Collection<String> colls = new ClassNameLocator().locateClassNames("org.sopo.component");
		System.out.println(colls);
	}
}
