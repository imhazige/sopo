package com.kazge.sopo.template;

import org.w3c.dom.Comment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLBodyElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLHeadElement;
import org.w3c.dom.html.HTMLLinkElement;
import org.w3c.dom.html.HTMLScriptElement;
import org.w3c.dom.html.HTMLTitleElement;

import com.kazge.sopo.Constants;
import com.kazge.sopo.Engine;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.component.Body;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.component.CssLink;
import com.kazge.sopo.component.Head;
import com.kazge.sopo.component.Literal;
import com.kazge.sopo.component.ScriptLink;
import com.kazge.sopo.component.TagComponent;
import com.kazge.sopo.component.Title;
import com.kazge.sopo.component.V;
import com.kazge.sopo.util.XmlUtils;


public class BaseTemplate
{
	protected String getAttribute(Node node,String name)
	{
		if (null == node || null == node.getAttributes() || null == node.getAttributes().getNamedItem(name))
		{
			return null;	
		}
		
		return node.getAttributes().getNamedItem("href").getNodeValue();
	}
	
	protected Component parseNode(Component parent, Node node) throws Exception
	{
		if (parent instanceof V)
		{
			return null;
		}
		
		//ignore form
		if (node instanceof HTMLFormElement)
		{
			parseChild(node, parent);
			
			return null;
		}		
		
		Component component = null;
		if (node instanceof Text)
		{
			component = new Literal(node.getTextContent());
		}
		else
		{
			String tagName = node.getNodeName();
			if (-1 == tagName.indexOf(":"))
			{
				if (null != node.getAttributes() && null != node.getAttributes().getNamedItem(Constants.SOPO_TYPE_NS))
				{
					Node stype = node.getAttributes().getNamedItem(Constants.SOPO_TYPE_NS);
					String type = stype.getNodeValue();
					Class<? extends Component> clazz = Engine.instance().resolveComponent(type);
					if (null == clazz)
					{
						throw new SopoException("can't find Component as [%s]", type);
					}
					component = parseSopoComponent(node, clazz);					
				}
				else
				{
					component = parsePlainComponent(node);
				}
			}
			else
			{
				Class<? extends Component> clazz = Engine.instance()
						.resolveComponent(node.getNodeName());
				if (null == clazz)
				{
					throw new SopoException("can't find Component as [%s]", node.getNodeName());
				}
				component = parseSopoComponent(node, clazz);
			}			
		}

		if (null != component && !(component instanceof V))
		{	
			parent.addComponent(component);			
		}				
		if (null == component.getTagName())
		{
			component.setTagName(node.getNodeName());
		}
		
		return component;	
	}

	protected Component parseSopoComponent(Node node, Class<? extends Component> clazz) throws Exception
	{		
		Component component;		
		try
		{
			component = clazz.newInstance();			
		}
		catch (InstantiationException e)
		{
			throw new SopoException("component must have a non-arguments constructor.");
		}
		setAttributes(node, component);
		Node child = node.getFirstChild();
		while (null != child)
		{
			parseNode(component, child);
			child = child.getNextSibling();
		}
		return component;
	}
	
	protected Component mappingComponent(final Node node)
	{
		Component component = null;
		String nodeName = node.getNodeName();
		
		if (node instanceof DocumentType)
		{
			component = new Literal(XmlUtils.node2String(node));
		}
		else if (node instanceof HTMLHeadElement)
		{
			component = new Head();
		}
		else if (node instanceof HTMLTitleElement)
		{
			component = new Title();
		}
		else if (node instanceof HTMLLinkElement)
		{
			component = new CssLink();
		}
		else if (node instanceof HTMLScriptElement)
		{
			component = new ScriptLink();
		}
		else if (node instanceof Comment)
		{
			component = new Literal(XmlUtils.node2String(node));
		}else if("br".equalsIgnoreCase(nodeName)){
			component = new Literal("<br>");
		}else if (node instanceof HTMLBodyElement)
		{
			component = new Body();
		}		
		else
		{
			component = new TagComponent();			
		}		
		
		return component;
	}
	
	protected void parseChild(final Node node,Component component) throws Exception
	{
		Node child = node.getFirstChild();
		while (null != child)
		{
			parseNode(component, child);
			child = child.getNextSibling();
		}
	}

	protected Component parsePlainComponent(final Node node) throws Exception
	{
		Component component = mappingComponent(node);
		
		if (null == component)
		{
			return null;
		}

		setAttributes(node, component);

		parseChild(node, component);
		
		return component;
	}

	protected void setAttributes(Node node, Component component)
	{
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; null != map && i < map.getLength(); i++)
		{
			Node attr = node.getAttributes().item(i);
			String key = attr.getNodeName();
			String value = attr.getNodeValue();
			component.setAttribute(key, value);
		}
	}
}
