package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class Anchor extends TagComponent
{
	public Anchor()
	{
	}
	
	public Anchor(String href)
	{
		this(href,null);
	}
	
	public Anchor(String href,String text)
	{
		setHref(href);
		setText(text);
	}
	
	@Override
	public String getTagName()
	{
		return "a";
	}

	public String getHref()
	{
		return getAttributes().get("href");
	}

	public void setHref(String src)
	{
		getAttributes().put("href", src);
	}
	
	public void setText(String text)
	{
		getAttributes().put("text", text);
	}
	
	public String getText()
	{
		return getAttributes().get("text");
	}
	
	@Override
	protected void renderAttribute(String name, String value, MarkupWriter writer)
	{
		if ("text".equalsIgnoreCase(name))
		{
			return;
		}
		super.renderAttribute(name, value, writer);
	}
	
	@Override
	protected void renderChildren(MarkupWriter writer)
	{
		if (null != getText())
		{
			writer.write(getText());	
		}		
		super.renderChildren(writer);
	}

	public void setTarget(String string)
	{
		setAttribute("target", string);		
	}
	
	public String getTarget()
	{
		return getAttribute("target");		
	}
}
