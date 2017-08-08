package com.kazge.sopo.component;


import com.kazge.sopo.MarkupWriter;

public class Label extends TagComponent
{
	public Label()
	{
		this("");
	}
	
	public Label(String text)
	{
		setText(text);
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
	
	public String getText()
	{		
		return getAttribute("text");
	}

	public void setText(String text)
	{
		setAttribute("text", text);
	}

	@Override
	public String getTagName()
	{
		return "label";
	}
}
