package com.kazge.sopoexample.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.kazge.sopoexample.common.FileUtils;
import com.kazge.sopoexample.common.xml.IXmlSaxListener;
import com.kazge.sopoexample.common.xml.SAXEventParser;

import com.kazge.sopo.DefaultNavigate;
import com.kazge.sopo.Navigate;
import com.kazge.sopo.Request;

public class AppConfig extends com.kazge.sopo.AppConfig
{
	private Map<String, List<Map<String,String>>> grid2Config;
	private String gridId;
	
	@Override
	public Navigate getNavigate()
	{
		return new DefaultNavigate(){
			@Override
			public String getPagePackageName()
			{
				return "test.ui.web.page";
			}
		};
	}

	@Override
	public Map<String, String> registerLibrary()
	{
		Map<String, String> tags = new HashMap<String, String>();
		tags.put("t", "test.ui.component");

		return tags;
	}

	@Override
	public boolean isDevelopMode()
	{
		return true;
	}

	public Map<String, List<Map<String, String>>> getGrid2Config()
	{
		debugConfig();
		return grid2Config;
	}
	
	private void debugConfig()
	{
		try
		{
			String path = Request.getCurrentInstance().getSession().getServletContext().getRealPath("grid2config.xml");
			String xml = FileUtils.readFileToString(new File(path));
			SAXEventParser parser = new SAXEventParser();
			grid2Config = new HashMap<String, List<Map<String,String>>>();
			gridId = null;
			parser.addSAXListener("/grids/grid", new IXmlSaxListener() {

				public void content(String text)
				{
				}

				public void endElement(String uri, String name, String globalName)
				{
				}

				public void startElement(String uri, String name, String globalName, Attributes attributes)
				{
					gridId = attributes.getValue("id");
					grid2Config.put(gridId, new ArrayList<Map<String,String>>());
				}
			});

			parser.addSAXListener("/grids/grid/col", new IXmlSaxListener() {

				public void content(String text)
				{
				}

				public void endElement(String uri, String name, String globalName)
				{
				}

				public void startElement(String uri, String name, String globalName, Attributes attributes)
				{
					List<Map<String,String>> cols = (List<Map<String,String>>)grid2Config.get(gridId);
					Map<String,String> map = new HashMap<String, String>();
					for (int i = 0; null != attributes && i < attributes.getLength(); i++)
					{
						String key = attributes.getLocalName(i);
						String value = attributes.getValue(i);
						map.put(key, value);						
					}
					cols.add(map);
				}
			});

			parser.parse(xml);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}	
	}
	
	@Override
	public String getRequestEncoding()
	{
		return "gb2312";
	}
	
	@Override
	public String getResponseEncoding()
	{
		return "gb2312";
	}
}
