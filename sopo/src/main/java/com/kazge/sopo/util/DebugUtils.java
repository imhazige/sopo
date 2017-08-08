package com.kazge.sopo.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import com.kazge.sopo.Request;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.component.TagComponent;

public class DebugUtils
{
	@SuppressWarnings("unchecked")
	public static String getChildrenTreeStr(Component container, String indent)
	{
		try
		{
			StringBuilder sb = new StringBuilder();

			Iterator<Component> it = container.iterator();
			while (null != it && it.hasNext())
			{
				Component comp = it.next();
				String tag = "";
				if (comp instanceof TagComponent)
				{
					tag = ((TagComponent) comp).getTagName();
				}
				Map<String, String> attrs = (Map<String, String>) BeanUtils.getNestedProperty(comp, "attributes");
				sb.append(String.format("%1$s<%2$s[%4$s] %5$s >\n%3$s", indent, comp.getClass().getSimpleName(),
										getChildrenTreeStr(comp, indent + indent), tag, attrs));
				sb.append(String.format("%1s</%2s>\n", indent, comp.getClass().getSimpleName(),
										getChildrenTreeStr(comp, indent + indent)));
			}

			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return e.getMessage();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void printRequestParams(Request request)
	{
		Enumeration en = request.getHttpServletRequest().getParameterNames();
		
		while(en.hasMoreElements())
		{
			String name = (String)en.nextElement();
			String value  = request.getHttpServletRequest().getParameter(name);
			Log.debug("%s=%s",name,value);
		}
	}
}
