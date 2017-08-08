package com.kazge.sopo;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kazge.sopo.page.Page;
import com.kazge.sopo.util.WebUtils;


public class Request
{
	private HttpServletRequest httpServletRequest;

	private Page page;
	
	private HttpSession session;
	
	private boolean post;
	
	private String servletPath;
	
	public Request(HttpServletRequest req)
	{
		httpServletRequest = req;
		session = req.getSession();
		post = "post".equalsIgnoreCase(httpServletRequest.getMethod());
		setCurrentInstance(this);
	}
	
	public String getContextUrl(String url)
	{
		return WebUtils.buildContextUrl(httpServletRequest, url);
	}
	
	public String getContextPath()
	{
		return httpServletRequest.getContextPath();
	}
	
	public String getServletPath()
	{
		if (null != servletPath)
		{
			return servletPath;
		}				
		
		servletPath = WebUtils.getServletPath(httpServletRequest);		
		
		return servletPath;
	}
	
	@SuppressWarnings("unchecked")
	public Map getParameterMap()
	{
		return httpServletRequest.getParameterMap();
	}
	
	public String getParameter(String name)
	{
		if (null == name)
		{
			return null;
		}
		return httpServletRequest.getParameter(name);
	}

	public HttpSession getSession()
	{
		return session;
	}

	public Page getPage()
	{
		return page;
	}

	public HttpServletRequest getHttpServletRequest()
	{
		return httpServletRequest;
	}
	
	public boolean isPost()
	{
		return post;
	}
	
	private static ThreadLocal<Request> _currentInstance = new ThreadLocal<Request>() {
		protected Request initialValue() {
			return null;
		}
	};
	
    public static Request getCurrentInstance()
    {
        return _currentInstance.get();
    }

    protected static void setCurrentInstance(Request request)
    {
        _currentInstance.set(request);
    }		
	
	public void release(){
		httpServletRequest = null;
		setCurrentInstance(null);
	}

	public void setPage(Page page)
	{
		this.page = page;
	}

	public String getRequestURI()
	{
		return httpServletRequest.getRequestURI();
	}

	public Object getAttribute(String key)
	{
		return httpServletRequest.getAttribute(key);
	}
	
	public void setAttribute(String key,Object value)
	{
		httpServletRequest.setAttribute(key, value);
	}
}
