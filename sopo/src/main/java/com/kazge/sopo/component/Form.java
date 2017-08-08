package com.kazge.sopo.component;

import java.io.Serializable;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.page.Page;
import com.kazge.sopo.util.BeanUtils;
import com.kazge.sopo.util.StringUtils;


public class Form extends TagComponent
{
	public Form()
	{
		setId("_frm");
	}
	
	@Override
	public String getTagName()
	{
		return "form";
	}
	
	@Override
	public void render(MarkupWriter writer)
	{		
		if (StringUtils.isEmpty(getAttributes().get("method")))
		{
			getAttributes().put("method", "post");
		}			
		if (!getPage().getViewSate().isEmpty())
		{

			Hidden viewStateElement = new Hidden();
			viewStateElement.setName(Page.PARAM_VIEWSTATE);
			viewStateElement.setValue(BeanUtils.object2String((Serializable) getPage().getViewSate()));
			addComponent(viewStateElement);
		}
		super.render(writer);
	}
}
