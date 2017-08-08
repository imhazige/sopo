package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class Title extends TagComponent
{
	public Title()
	{
	}
	
	public Title(String title)
	{
		this.setTitle(title);
	}
	
	@Override
	public String getTagName()
	{
	
		return "title";
	}

	public String getTitle()
	{
		return getAttribute("value");
	}

	public void setTitle(String title)
	{
		setAttribute("value", title);
	}

	@Override
	protected void renderAttribute(String name, String value, MarkupWriter writer)
	{
		if ("value".equalsIgnoreCase(name))
		{
			return;
		}
		super.renderAttribute(name, value, writer);
	}
}
