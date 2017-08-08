package com.kazge.sopoexample.common.bean;

import java.util.Map;



public class SelectDescriptor extends BasePropertyDescriptor implements IExample
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6486749972936609605L;

	private boolean inputable = false;
	
	private IExample example;	
	
	public SelectDescriptor(IExample selectExample)
	{
		example = selectExample;
	}
	
	public boolean isInputable()
	{
		return inputable;
	}
	
	public void setInputable(boolean inputable)
	{
		this.inputable = inputable;
	}	

	public Map<String, String> getExamples()
	{
		return example.getExamples();
	}	
}
