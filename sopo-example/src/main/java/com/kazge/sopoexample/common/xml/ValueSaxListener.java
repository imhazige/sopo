package com.kazge.sopoexample.common.xml;


import org.xml.sax.Attributes;

import com.kazge.sopoexample.common.BeanUtils;
import com.kazge.sopoexample.common.convert.IConvertor;

public class ValueSaxListener implements IXmlSaxListener {
//	private static final Logger logger
		
	private IConvertor convertor;
	private Object bean;
	private String proName;
	
	public ValueSaxListener(Object bean,String argName,IConvertor convertor){
		this.bean = bean;
		this.convertor = convertor;
		this.proName = argName;
	}
	
	public void content(String text) {
			
	}

	public void endElement(String uri, String name, String globalName) {
	}

	public void startElement(String uri, String name, String globalName,
			Attributes attributes) {
		
		String value = attributes.getValue("value");
		Object obj = convertor.convert(value);
		try {			
			BeanUtils.setNestedProperty(bean, proName, obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("parse value error:" + ex.getMessage());
		}
	}

}
