package com.kazge.sopoexample.component.grid;

import test.common.StringUtils;
import test.common.query.FilterCondition;
import test.common.query.FilterRelation;
import test.common.query.FilterType;
import web.sopo.Request;
import web.sopo.component.Text;

public class TextQueryCell extends QueryCell
{
	private String cv;
	
	@Override
	public FilterCondition resolve(Request request)
	{
		cv = request.getParameter(getName());
		if (StringUtils.isBlank(cv))
		{
			return null;
		}
		
		FilterCondition fc = new FilterCondition(FilterType.LEFTLIKE,FilterRelation.AND,getDescriptor());
		fc.setConditionValue(cv);
		
		return fc;
	}

	@Override
	public void beforeRender()
	{
		Text text = new Text();
		text.setStyle("width", "98%");
		if (null != cv)
		{
			text.setValue(cv);
		}
		text.setName(getName());
		
		addComponent(text);
	}
	
	private String getName()
	{
		return "tq_" + getDescriptor().getName();
	}	
}
