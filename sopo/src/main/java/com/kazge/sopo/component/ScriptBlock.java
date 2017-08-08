package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;

public class ScriptBlock extends TagComponent
{
	private StringBuilder buff = new StringBuilder();

	public ScriptBlock()
	{
		this("");
	}

	public ScriptBlock(String js)
	{
		setAttribute("type", "text/javascript");
		buff.append(js);
	}

	@Override
	public String getTagName()
	{
		return "script";
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
