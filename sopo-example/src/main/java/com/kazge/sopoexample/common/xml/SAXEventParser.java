package com.kazge.sopoexample.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXEventParser
{
	private Map<String, IXmlSaxListener> lismap;

	public void addSAXListener(String path, IXmlSaxListener listener)
	{
		getLismap().put(path, listener);
	}

	public void parse(String xml) throws ParserConfigurationException, SAXException, IOException
	{
		DefaultHandler handler = new SAXListenerHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(new InputSource(new StringReader(xml)), handler);
	}

	public void parse(InputStream in) throws ParserConfigurationException, SAXException, IOException
	{
		DefaultHandler handler = new SAXListenerHandler();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(in, handler);
	}

	private final class SAXListenerHandler extends DefaultHandler
	{
		private Stack<String> stack = new Stack<String>();

		private String getPath()
		{
			Iterator<String> it = stack.iterator();
			String path = "";
			while (it.hasNext())
			{
				path += "/" + it.next();
			}
			return path;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
		{
			stack.push(name);
			String path = getPath();
			if (getLismap().containsKey(path))
			{
				getLismap().get(path).startElement(uri, name, path, attributes);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			String path = getPath();
			String content = new String(ch, start, length);
			if (getLismap().containsKey(path))
			{
				getLismap().get(path).content(content.trim());
			}
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException
		{
			String path = getPath();
			if (getLismap().containsKey(path))
			{
				getLismap().get(path).endElement(uri, name, path);
			}
			stack.pop();
		}
	}

	private Map<String, IXmlSaxListener> getLismap()
	{
		if (null == lismap)
		{
			lismap = new HashMap<String, IXmlSaxListener>();
		}

		return this.lismap;
	}
}
