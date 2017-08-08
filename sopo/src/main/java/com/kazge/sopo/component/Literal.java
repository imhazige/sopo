package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class Literal extends Component
{
	public Literal()
	{
		this("");
	}
	
	public Literal(String html)
	{
		setHtml(html);
	}
	
	@Override
	public void render(MarkupWriter writer)
	{	
		if (null != getHtml())
		{
			writer.writeRaw(getHtml());	
		}		
		renderChildren(writer);
	}

	public String getHtml()
	{
		return getAttribute("html");
	}

	public void setHtml(String html)
	{
		setAttribute("html", html);
	}	
}
