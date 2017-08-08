package com.kazge.sopo.component;

public class Text extends AbstractInputField
{
	@Override
	protected String getType()
	{
		return "text";
	}
	
	public void setEditable(boolean b)
	{
		if (!b)
		{
			setAttribute("readonly", "readonly");
		}else{
			removeAttribute("readonly");
		}
	}
	
	public boolean isEditable(){
		return null == getAttribute("readonly");
	}
}
