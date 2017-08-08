package com.kazge.sopoexample.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.xml.sax.SAXException;

import com.kazge.sopoexample.common.convert.IConvertor;
import com.kazge.sopoexample.common.convert.StringConvertor;
public class XmlDigester {
	Map<String,IXmlSaxListener> rules = new HashMap<String, IXmlSaxListener>();
	
	private Object bean;
	private String rootName;
	
	public XmlDigester(Object obj,String rootName){
		bean = obj;
		this.rootName = "/" + rootName;
	}
	
	public void parse(String classResourceXml){
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream(classResourceXml);
			parse(in);
		} catch (SAXException ex) {
			ex.printStackTrace();
			throw new RuntimeException("parse xml error:" + ex.getMessage());
		}finally{
			if (null != in){
				try {
					in.close();
				} catch (IOException ex) {
					
				}
			}
		} 
	}
	
	public void parse(InputStream in) throws SAXException{
		SAXEventParser parser = new SAXEventParser(); 
		
		Iterator<Map.Entry<String, IXmlSaxListener>> it = rules.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String, IXmlSaxListener> entry = it.next();
			String path = entry.getKey();
			IXmlSaxListener listener = entry.getValue();
			parser.addSAXListener(path, listener);
		}
		
		try {
			parser.parse(in);
		} catch (Exception ex) {
			throw new SAXException(ex);
		}
	}
	
	public void addAttrRule(String name,IConvertor convertor){
		String proName = name;
		name = rootName + "/" + name;
		rules.put(name, new ValueSaxListener(bean,proName,convertor));
	}	
	
	public void addAttrRule(String name){
		addAttrRule(name,new StringConvertor());
	}
	
	public void addTextRule(String name,IConvertor convertor){
		String proName = name;
		name = rootName + "/" + name;
		rules.put(name, new TextSaxListener(bean,proName,convertor));
	}
	
	public void addTextRule(String name){
		addTextRule(name,new StringConvertor());
	}

}
