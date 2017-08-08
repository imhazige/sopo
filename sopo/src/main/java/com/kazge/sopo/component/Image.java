package com.kazge.sopo.component;

public class Image extends TagComponent
{
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
		return "img";
	}

}
