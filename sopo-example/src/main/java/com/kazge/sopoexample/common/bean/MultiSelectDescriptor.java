package com.kazge.sopoexample.common.bean;

public abstract class MultiSelectDescriptor extends SelectDescriptor
{

	public MultiSelectDescriptor(IExample selectExample)
	{
		super(selectExample);
	}

	abstract public void modify(Object bean, String[] values);
	
	abstract public String[] displays(Object bean);
}
