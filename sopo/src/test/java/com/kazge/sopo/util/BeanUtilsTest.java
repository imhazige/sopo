package com.kazge.sopo.util;

import java.util.HashMap;
import java.util.Map;

import com.kazge.sopo.util.BeanUtils;

import junit.framework.TestCase;

public class BeanUtilsTest extends TestCase
{
	@SuppressWarnings("unchecked")
	public void testSerialize()
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		Map<String, String> submap = new HashMap<String, String>();
		submap.put("hello", "中文");
		submap.put("yaha", "sopo");
		map.put("1", "1");
		map.put("2", 2);
		map.put("3", submap);
		String objstr = BeanUtils.object2String(map);
		System.out.println(objstr);
		HashMap<String, Object> map2 = (HashMap<String, Object>)BeanUtils.readObject(objstr);
		System.out.println(map2);
		assertEquals(map, map2);
	}
}
