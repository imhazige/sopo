package com.kazge.sopo;

import java.io.PrintWriter;
import java.util.Stack;

import com.kazge.sopo.util.WebUtils;


public class MarkupWriterImpl implements MarkupWriter
{
	private PrintWriter writer;
	private Stack<String> stack = new Stack<String>();
	
	public MarkupWriterImpl(Response response)
	{
		writer = response.getWriter();
	}
	
	public void cdata(String content)
	{
		writer.write("<![CDATA["+ content +"]]>");		
	}

	public void comment(String text)
	{
		writer.write("<!--"+ WebUtils.encodeHtml(text) +"-->");				
	}

	public void end()
	{
		String tag = stack.pop();
		if (null == tag)
		{
			throw new RuntimeException("markup queue is not matched.");
		}
		writeRawf("</%s>",tag);
	}

	public void start(String tag, Object... args)
	{
		if (null != args && 0 != args.length % 2)
		{
			throw new RuntimeException("attributes for [" + tag + "] not macthed:" + args);
		}
		stack.push(tag);
		writeRaw("<" + tag);
		for (int i = 0 ; null != args && i < args.length ; i+=2)
		{
			writef(" %s=%s",args[i],args[i+1]);
		}
		writeRaw(" >");
	}

	public void write(String text)
	{
		writer.write(WebUtils.encodeHtml(text));						
	}

	public void writeRaw(String text)
	{
		writer.write(text);				
	}
	
	public void writeRawf(String format, Object... args)
	{
		writer.write(String.format(format, args));				
	}

	public void writef(String format, Object... args)
	{
		writer.write(WebUtils.encodeHtml(String.format(format, args)));				
	}

}
