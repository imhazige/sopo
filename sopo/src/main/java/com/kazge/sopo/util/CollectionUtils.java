package com.kazge.sopo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CollectionUtils
{
	@SuppressWarnings("unchecked")
	public static ArrayList clone(ArrayList orgin)
	{
		if (null == orgin)
		{
			return null;
		}
		Iterator it = orgin.iterator();
		ArrayList narr = new ArrayList();
		while (it.hasNext())
		{
			narr.add(it.next());
		}

		return narr;
	}

	@SuppressWarnings("unchecked")
	public static HashMap clone(HashMap orgin)
	{
		if (null == orgin)
		{
			return null;
		}
		Iterator it = orgin.keySet().iterator();
		HashMap narr = new HashMap();
		while (it.hasNext())
		{
			Object key = it.next();
			Object value = orgin.get(key);
			narr.put(key, value);
		}

		return narr;
	}
}
