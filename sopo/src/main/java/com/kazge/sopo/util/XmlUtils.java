package com.kazge.sopo.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;

public class XmlUtils
{

	public static String node2String(Node node)
	{
		switch (node.getNodeType())
		{			
			case Node.DOCUMENT_TYPE_NODE:
			{
				DocumentType docType = (DocumentType) node;
				String s = "<!DOCTYPE " + docType.getName();

				if (null != docType.getPublicId())
				{
					s += " PUBLIC \"" + docType.getPublicId() + "\"";
				}
				else
				{
					s += " SYSTEM ";
				}

				if (null != docType.getSystemId())
				{
					s += " \"" + docType.getSystemId() + "\"";
				}

				s += " >";

				return s;

			}
			case Node.COMMENT_NODE :
			{
				if (node.getNodeValue().startsWith("[CDATA["))
				{
					return "<!" + node.getNodeValue() + ">";
				}
				//for node of real comment,just pass by to default
			}
			default:
			{
				StringWriter sw = new StringWriter();
				try
				{
					Transformer t = TransformerFactory.newInstance().newTransformer();
					t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					t.transform(new DOMSource(node), new StreamResult(sw));
				}
				catch (TransformerException te)
				{
					System.out.println("nodeToString Transformer Exception");
				}
				return sw.toString();
			}
		}

	}
}
