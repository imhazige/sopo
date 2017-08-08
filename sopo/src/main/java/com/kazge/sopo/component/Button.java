package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.Request;

public class Button extends AbstractFormField
{	
	public Button()
	{
		this("");		
	}
	
	public Button(String argValue)
	{
		this.setValue(argValue);
		this.setName(argValue);
		setAttribute("type", "submit");
	}
	
	public void update(Request request)
	{
		if (!request.getParameterMap().containsKey(getName()))
		{
			return;
		}
	}
	
	@Override
	protected void renderChildren(MarkupWriter writer)
	{			
		writer.write(getValue());
	}

	@Override
	public String getTagName()
	{
		return "button";
	}	
}
