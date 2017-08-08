package com.kazge.sopo;

import com.kazge.sopo.page.Page;
import com.kazge.sopo.reflection.ClassCache;
import com.kazge.sopo.util.StringUtils;

public class DefaultNavigate implements Navigate
{
	@SuppressWarnings("unchecked")
	public Class<? extends Page> mapping(Request request)
	{
		// as o/abc.*,the o.abc is the page class
		String pageStr = request.getServletPath();
		int index = pageStr.indexOf(".");
		index = -1 != index ? index : pageStr.length();
		pageStr = pageStr.substring(0, index);
		index = pageStr.lastIndexOf("/");
		if (-1 != index)
		{
			pageStr = pageStr.substring(0,index + 1) + StringUtils.upperFirst(pageStr.substring(index + 1));
		}
		pageStr = pageStr.replaceAll("/", ".");
		pageStr = mapping2ClassName(pageStr);		

		try
		{
			Class<? extends Page> clazz = (Class<? extends Page>) ClassCache.instance().forName(pageStr);

			return clazz;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public String mapping2ClassName(String pageName){
		pageName = StringUtils.upperFirst(pageName) + "Page";
		return getPagePackageName() + pageName;
	} 
	
	public String getPagePackageName()
	{
		return "app.pages";
	}
}
