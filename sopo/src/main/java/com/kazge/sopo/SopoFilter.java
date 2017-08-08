package com.kazge.sopo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kazge.sopo.util.Log;

@SuppressWarnings("serial")
public class SopoFilter implements Filter
{
	private Engine engine;
	private ServletContext context;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try
		{
			if (!engine.run(req, resp))
			{
				chain.doFilter(request, response);
			}
		}
		catch (Exception e)
		{
			Log.error(e);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		context = filterConfig.getServletContext();
		
		//config
		engine = Engine.init(filterConfig);
	}

	public void destroy()
	{
		engine.destroy(context);
		context = null;
	}

}