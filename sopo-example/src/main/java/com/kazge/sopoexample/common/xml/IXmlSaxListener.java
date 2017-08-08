package com.kazge.sopoexample.common.xml;

import org.xml.sax.Attributes;

public interface IXmlSaxListener {
	void startElement(String uri,String name,String globalName,Attributes attributes);
	void content(String text);
	void endElement(String uri,String name,String globalName);
}
