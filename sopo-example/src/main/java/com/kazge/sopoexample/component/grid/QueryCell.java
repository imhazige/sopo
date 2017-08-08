package com.kazge.sopoexample.component.grid;

import test.common.bean.PropertyDescriptor;
import test.common.query.FilterCondition;
import web.sopo.MarkupWriter;
import web.sopo.Request;
import web.sopo.component.TagComponent;

public abstract class QueryCell extends TagComponent
{	
	private PropertyDescriptor descriptor;
	
	abstract public FilterCondition resolve(Request request);

	public PropertyDescriptor getDescriptor()
	{
		return descriptor;
	}

	public void setDescriptor(PropertyDescriptor descriptor)
	{
		this.descriptor = descriptor;
	}

	@Override
	public String getTagName()
	{
		return null;
	}	
	
	@Override
	public void render(MarkupWriter writer)
	{
		renderChildren(writer);
	}
}
