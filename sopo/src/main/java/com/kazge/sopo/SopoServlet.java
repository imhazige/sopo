package com.kazge.sopo;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kazge.sopo.util.Log;


@SuppressWarnings("serial")
public class SopoServlet extends HttpServlet
{
	private Engine engine;
	private ServletContext context;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		run(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{		
		run(req, resp);
	}
	
	private void run(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{		
		try
		{	
			engine.run(req, resp);
		}
		catch (Exception e)
		{
			Log.error(e);
		}
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);	
		context = config.getServletContext();
		engine = Engine.init(context);
	}
	
	@Override
	public void destroy()
	{
		engine.destroy(context);
		context = null;
		super.destroy();
	}
}