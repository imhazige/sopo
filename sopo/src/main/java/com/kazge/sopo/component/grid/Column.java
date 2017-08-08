package com.kazge.sopo.component.grid;

import java.util.Map;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.component.Label;
import com.kazge.sopo.component.Td;
import com.kazge.sopo.util.BeanUtils;

public class Column extends Td
{
	public Column()
	{
	}
	
	public Column(String name,String bindingName)
	{
		setName(name);
		setBindingName(bindingName);
	}
	
	public String getBindingName()
	{
		return getAttribute("bindingName");
	}
	
	public void setBindingName(String bindingName)
	{
		setAttribute("bindingName", bindingName);
	}
	public String getName()
	{
		return getAttribute("name");
	}
	public void setName(String name)
	{
		setAttribute("name", name);
	}
	
	@Override
	protected void renderAttribute(String name, String value, MarkupWriter writer)
	{
		if ("name".equalsIgnoreCase(name))
		{
			return;
		}else if ("bindingName".equalsIgnoreCase(name))
		{
			return;
		}
		super.renderAttribute(name, value, writer);
	}	
	
	@Override
	public void beforeRender()
	{
		addComponent(new Label(getName()));
	}
	
	public String display(Object data)
	{
		if (null == data || null == getBindingName())
		{
			return "";
		}
		if (data instanceof Map)
		{
			return (String) ((Map<?, ?>) data).get(getBindingName());
		}

		try
		{
			Object value = BeanUtils.getNestedProperty(data, getBindingName());
				
			return null == value ? "":value.toString();
		}
		catch (Exception e)
		{
			throw new SopoException(e,"can't get the value with the column:" + getBindingName());
		}	
	}
}
