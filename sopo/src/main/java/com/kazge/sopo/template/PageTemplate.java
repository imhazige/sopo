package com.kazge.sopo.template;

import java.io.StringReader;
import java.util.ArrayList;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.kazge.sopo.SopoException;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.page.Page;
import com.kazge.sopo.util.BeanUtils;
import com.kazge.sopo.util.Log;


public class PageTemplate extends BaseTemplate
{
	private Page page;

	public PageTemplate(Page argPage)
	{
		page = argPage;
	}

	public void parse(String html)
	{
		try
		{
			if (null == html)
			{
				Log.debug("no template for " + page.getClass().getName());

				return;
			}
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new StringReader(html)));
			Document doc = parser.getDocument();
			Node root = doc;
			BeanUtils.setNestedProperty(page.getRoot(), "children", new ArrayList<Component>());
			Node child = root.getFirstChild();
			while (null != child)
			{
				parseNode(page.getRoot(), child);
				child = child.getNextSibling();
			}
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
	}
	
}
