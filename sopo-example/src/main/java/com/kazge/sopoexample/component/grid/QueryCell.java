package com.kazge.sopoexample.component.grid;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;
import com.kazge.sopoexample.common.query.FilterCondition;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.Request;
import com.kazge.sopo.component.TagComponent;

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
