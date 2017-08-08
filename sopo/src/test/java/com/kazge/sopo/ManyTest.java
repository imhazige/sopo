package com.kazge.sopo;

import junit.framework.TestCase;

public class ManyTest extends TestCase
{
	public void testReplace()
	{
		assertTrue("a/b/c.html".equals("a.b.c".replaceAll("\\.", "/") + ".html"));
	}

	
}
