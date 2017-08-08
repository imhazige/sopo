package com.kazge.sopo.component;

import com.kazge.sopo.Request;

public abstract class AbstractInputField extends AbstractFormField
{
	public AbstractInputField()
	{
		getAttributes().put("type", getType());
	}

	abstract protected String getType();
	
	@Override
	public String getTagName()
	{
		return "input";
	}

	public void update(Request request)
	{
		setValue(request.getParameter(getName()));
	}
}
