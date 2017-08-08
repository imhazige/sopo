package com.kazge.sopo.component;

import com.kazge.sopo.Request;

public class Checkbox extends AbstractFormField
{
	public Checkbox()
	{
		this(false);
	}
	
	public Checkbox(boolean checked)
	{
		getAttributes().put("type", "checkbox");
		if (checked)
		{
			setChecked(true);
		}
	}
	
	@Override
	public String getTagName()
	{
		return "input";
	}

	public void update(Request request)
	{
		if ("on".equalsIgnoreCase(request.getParameter(getName())))
		{
			setChecked(true);
		}
	}
	
	public void setChecked(boolean value)
	{
		if (value)
		{
			getAttributes().put("checked", "true");		
		}else{
			getAttributes().remove("checked");
		}				
	}
	
	public boolean isChecked()
	{
		if (getAttributes().containsKey("checked"))
		{
			return true;
		}
		
		return false;
	}	
}
