package com.kazge.sopoexample.common.convert;




public class BooleanConvertor implements IConvertor{

	public Object convert(String data) {
		return Boolean.parseBoolean(data);
	}

}
