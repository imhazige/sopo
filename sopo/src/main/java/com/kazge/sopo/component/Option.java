package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class Option extends TagComponent
{
	public Option()
	{
		
	}
	
	public Option(String text)
	{
		this(text,text);
	}
	
	public Option(String text,String value)
	{
		setText(text);
		setValue(value);
	}
	
	@Override
	public String getTagName()
	{
		return "option";
	}
	
	public void setText(String text)
	{
		setAttribute("text", text);
	}
	
	public String getText()
	{
		return getAttribute("text");
	}
	
	public void setValue(String text)
	{
		setAttribute("value", text);
	}
	
	public String getValue()
	{
		return getAttribute("value");
	}
	
	public void setSelected(boolean bl)
	{
		if (!bl)
		{
			getAttributes().remove("selected");
		}else{
			getAttributes().put("selected","selected");
		}
	}
	
	public boolean isSelected()
	{
		return getAttributes().containsKey("selected");
	}	
	
	@Override
	protected void renderChildren(MarkupWriter writer)
	{
		writer.write(getText());
		super.renderChildren(writer);
	}
	
}
