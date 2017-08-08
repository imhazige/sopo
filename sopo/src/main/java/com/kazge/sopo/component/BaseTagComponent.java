package com.kazge.sopo.component;

public class BaseTagComponent extends TagComponent
{
	private String tag;
	
	public BaseTagComponent()
	{
	}
	
	public BaseTagComponent(String argTag)
	{
		tag = argTag;
	}
	
	@Override
	public String getTagName()
	{
		return tag;
	}
	
	@Override
	public BaseTagComponent clone()
	{
		BaseTagComponent nc = (BaseTagComponent)super.clone();
		
		nc.tag = tag;
		
		return nc;
	}
}
