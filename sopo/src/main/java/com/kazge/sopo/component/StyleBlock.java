package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class StyleBlock extends TagComponent
{
	private StringBuilder buff = new StringBuilder();

	public StyleBlock()
	{
		this("");
	}

	public StyleBlock(String js)
	{
		buff.append(js);
	}

	@Override
	public String getTagName()
	{
		return "style";
	}

	public void append(String js)
	{
		buff.append(js);
	}

	public void append(String format, Object... args)
	{
		buff.append(String.format(format, args));
	}

	@Override
	protected void renderChildren(MarkupWriter writer)
	{
		writer.writeRaw(buff.toString());
	}
}
