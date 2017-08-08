package com.kazge.sopo.asset;

import com.kazge.sopo.Request;

public class ContextAsset extends Asset
{
	public ContextAsset(String argUri)
	{
		super(argUri);
	}
	
	@Override
	public String toUri()
	{
		if (null == uri)
		{
			return null;
		}		
		
		uri = Request.getCurrentInstance().getContextUrl(uri);
		
		return uri;
	}	
}
