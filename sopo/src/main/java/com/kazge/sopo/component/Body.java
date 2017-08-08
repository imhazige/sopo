package com.kazge.sopo.component;

import java.io.Serializable;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.page.Page;
import com.kazge.sopo.util.BeanUtils;



public class Body extends TagComponent
{	
	@Override
	protected void renderChildren(MarkupWriter writer)
	{
		writer.start("form", "method","\"post\"","id","_form");
		
		super.renderChildren(writer);
		
		if (!getPage().getViewSate().isEmpty())
		{

			Hidden viewStateElement = new Hidden();
			viewStateElement.setName(Page.PARAM_VIEWSTATE);
			viewStateElement.setValue(BeanUtils.object2String((Serializable) getPage().getViewSate()));
			viewStateElement.render(writer);
		}
		
		writer.end();
	}	
}
