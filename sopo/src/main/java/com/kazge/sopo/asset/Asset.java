package com.kazge.sopo.asset;

public class Asset
{
	public static final String ASSET = "__asset__";
	
	protected String uri;
	
	public Asset(String argUri)
	{
		uri = argUri;
	}
	
	public String toUri()
	{
		return uri;
	}
}
