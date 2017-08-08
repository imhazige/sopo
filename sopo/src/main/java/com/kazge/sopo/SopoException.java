package com.kazge.sopo;

public class SopoException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8410598884223491852L;

	public SopoException()
	{
		
	}
		
	public SopoException(String msg)
	{
		super(msg);
	}
	
	public SopoException(String format,Object ... args)
	{
		super(String.format(format, args));
	}
	
	public SopoException(Throwable throwable)
	{
		super(throwable);
	}
	
	public SopoException(Throwable throwable,String format,Object ... args)
	{
		super(String.format(format, args),throwable);
	}
}
