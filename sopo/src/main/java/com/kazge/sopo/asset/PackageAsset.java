package com.kazge.sopo.asset;

public class PackageAsset extends ContextAsset
{
	public PackageAsset(String argUri)
	{
		super(argUri);
	}
	
	@Override
	public String toUri()
	{
		uri = "/"+ ASSET + uri;
		
		return super.toUri();
	}
}
