package com.kazge.sopoexample.common.convert;


public class IntConvertor implements IConvertor{

	public Object convert(String data) {
		
		return Integer.parseInt(data);
	}

}
