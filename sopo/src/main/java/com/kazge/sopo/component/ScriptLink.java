package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.util.StringUtils;

public class ScriptLink extends TagComponent
{
	public ScriptLink()
	{
		this("");
	}
	
	public ScriptLink(String argSrc)
	{
		setSrc(argSrc);
		setAttribute("type", "text/javascript");
	}	

	public String getSrc()
	{
		return getAttributes().get("src");
	}

	public void setSrc(String src)
	{
		getAttributes().put("src", src);
	}

	@Override
	public String getTagName()
	{
		return "script";
	}
	
	@Override
	public void render(MarkupWriter writer)
	{
		String src = getSrc();
		if (StringUtils.isBlank(src))
		{
			return;
		}		
		setSrc(src);
		super.render(writer);
	}
}
