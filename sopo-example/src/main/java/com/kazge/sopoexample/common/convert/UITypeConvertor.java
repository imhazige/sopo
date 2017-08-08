package com.kazge.sopoexample.common.convert;

import com.kazge.sopoexample.common.bean.UIType;

public class UITypeConvertor implements IConvertor
{
	public Object convert(String data)
	{
		return UIType.valueOf(data);
	}

}
