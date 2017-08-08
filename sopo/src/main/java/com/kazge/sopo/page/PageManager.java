package com.kazge.sopo.page;

import com.kazge.sopo.Engine;
import com.kazge.sopo.Navigate;
import com.kazge.sopo.Request;
import com.kazge.sopo.Response;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.template.PageTemplate;

public class PageManager 
{
	private static final String KEY = PageManager.class.getName();
	
	private PageManager()
	{
	}	
	
	public static PageManager instance(Engine engine)
	{
		if (null == engine.getAppconfig().get(KEY))
		{
			engine.getAppconfig().put(KEY, new PageManager());
		}

		return (PageManager)engine.getAppconfig().get(KEY);
	}

	@SuppressWarnings("unchecked")
	public Page getPage(Request req,Response resp)
	{
		try
		{
			Navigate navigate = Engine.instance().getAppconfig().getNavigate();
			if (null == navigate)
			{
				throw new SopoException("AppConfig.getNavigate() should return a navigator.");
			}
			Class<? extends Page> clazz = navigate.mapping(req);
			if (null == clazz)
			{				
				return null;
			}
			Page page = null;								
			page = clazz.newInstance();
			page.setResponse(resp);
			req.setPage(page);
			String template = page.getTemplate();				
			new PageTemplate(page).parse(template);		
			
			return page;
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
}
