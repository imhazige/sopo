package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.util.StringUtils;


public class CssLink extends TagComponent
{
	public CssLink()
	{
		this("");
	}
	
	public CssLink(String argSrc)
	{
		setHref(argSrc);
		getAttributes().put("rel", "stylesheet");
		getAttributes().put("type", "text/css");
	}

	@Override
	public void render(MarkupWriter writer)
	{
		String href = getHref();
		if (StringUtils.isEmpty(href))
		{
			return;
		}		
		setHref(href);
		writer.writeRaw("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + href + "\">");
	}

	public String getHref()
	{
		return getAttributes().get("href");
	}

	public void setHref(String src)
	{
		getAttributes().put("href", src);
	}

	@Override
	public String getTagName()
	{
		return "link";
	}
}
