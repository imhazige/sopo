package com.kazge.sopoexample.common.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;

public class FilterCondition implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3176000254312901251L;
	private List<FilterCondition> subconditions;
	private Object conditionValue;
	private FilterType type;
	private FilterRelation relation;
	private PropertyDescriptor dispor;

	public FilterCondition(FilterType argType, FilterRelation argRelation, PropertyDescriptor argDispor)
	{
		type = argType;
		relation = argRelation;
		dispor = argDispor;
	}

	public Object getConditionValue()
	{
		return conditionValue;
	}

	public void setConditionValue(Object conditionValue)
	{
		this.conditionValue = conditionValue;
	}

	public List<FilterCondition> getSubconditions()
	{
		if (null == subconditions)
		{
			subconditions = new ArrayList<FilterCondition>();
		}
		return subconditions;
	}

	public FilterType getType()
	{
		return type;
	}

	public FilterRelation getRelation()
	{
		return relation;
	}

	public PropertyDescriptor getDispor()
	{
		return dispor;
	}
}
