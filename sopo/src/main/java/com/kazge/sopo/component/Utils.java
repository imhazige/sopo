package com.kazge.sopo.component;

import java.util.Iterator;
import java.util.Map;



public class Utils
{
	public static void copyAttributes(Component from,Component to)
	{	
		Iterator<Map.Entry<String, String>> it = from.getAttributes().entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			to.setAttribute(key, value);
		}
	}	
}
