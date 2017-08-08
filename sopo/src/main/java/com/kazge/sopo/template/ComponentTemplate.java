package com.kazge.sopo.template;

import java.io.StringReader;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLBodyElement;
import org.w3c.dom.html.HTMLHeadElement;
import org.w3c.dom.html.HTMLHtmlElement;
import org.w3c.dom.html.HTMLTitleElement;
import org.xml.sax.InputSource;

import com.kazge.sopo.SopoException;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.util.Log;
import com.kazge.sopo.util.StringUtils;


public class ComponentTemplate extends BaseTemplate
{
	private Component root;

	public ComponentTemplate(Component component)
	{
		root = component;
	}

	@Override
	protected Component parseNode(Component parent, Node node) throws Exception
	{
		String tagName = node.getNodeName();
		if (node instanceof HTMLHtmlElement || "html".equalsIgnoreCase(tagName))
		{
			parseChild(node, parent);

			return null;
		}
		else if (node instanceof HTMLHeadElement || "head".equalsIgnoreCase(tagName))
		{
			parseChild(node, parent);

			return null;
		}
		else if (node instanceof HTMLTitleElement)
		{
			return null;
		}
		else if (node instanceof HTMLBodyElement || "body".equalsIgnoreCase(tagName))
		{
			parseChild(node, parent);

			return null;
		}
		else
		{
			return super.parseNode(parent, node);
		}
	}

	public void parse(String html)
	{
		try
		{
			if (StringUtils.isBlank(html))
			{
				Log.debug("no template for " + root.getClass().getName());

				return;
			}
			DOMParser parser = new DOMParser();
			parser.parse(new InputSource(new StringReader(html)));
			Document doc = parser.getDocument();
			Node rootNode = doc;
			Node child = rootNode.getFirstChild();
			while (null != child)
			{
				parseNode(root, child);
				child = child.getNextSibling();
			}
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
	}
}
