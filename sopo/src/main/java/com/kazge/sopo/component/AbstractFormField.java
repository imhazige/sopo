package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.util.StringUtils;


public abstract class AbstractFormField extends TagComponent implements Updateable
{
	public Form getForm()
	{
		Component parent = getParent();
		while (null != parent)
		{
			if (parent instanceof Form)
			{
				return (Form)parent;
			}
			parent = parent.getParent();
		}
		return null;
	}
	
	@Override
	public void render(MarkupWriter writer)
	{
		Form form = getForm();
		if (null != form && StringUtils.isEmpty(getName()))
		{
			this.setName(this.getId());
		}
		super.render(writer);
	}
	
	public String getName()
	{
		return getAttributes().get("name");
	}
	
	public void setName(String value)
	{
		getAttributes().put("name",value);
	}
	
	public String getValue()
	{
		return getAttributes().get("value");
	}

	public void setValue(String value)
	{
		getAttributes().put("value", value);
	}
}
