package com.kazge.sopo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Response
{	
	private HttpServletResponse response;
	private PrintWriter writer; 
	
	private boolean reseted;
	
	public Response(HttpServletResponse argResponse)
	{
		this.response = argResponse;		
	}
	
	public void reset()
	{
		reseted = true;
		response.reset();		
	}	
	
	public void sendRedirect(String url)
	{
		try
		{
			reseted = true;
			response.sendRedirect(url);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public void setCharacterEncoding(String encoding)
	{
		response.setCharacterEncoding(encoding);
	}
	
	public void setContentType(String type)
	{
		response.setContentType(type);
	}
	
	public void append(String data)
	{
		
	}

	public boolean isReset()
	{
		return reseted;
	}

	public PrintWriter getWriter()
	{
		try
		{
			writer = response.getWriter();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		
		return writer;
	}	
}
