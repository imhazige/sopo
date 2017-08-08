package com.kazge.sopo.template;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.dom.DocumentTypeImpl;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import com.kazge.sopo.util.FileUtils;

/**
 * This program tests the NekoHTML parser's use of the HTML DOM implementation by printing the class names of all the
 * nodes in the parsed document.
 * 
 * @author Andy Clark
 * @version $Id: TestHTMLDOM.java,v 1.4 2010/03/03 06:19:24 zhouka Exp $
 */
public class TestHTMLDOM
{

	//
	// MAIN
	//

	/** Main. */
	public static void main(String[] argv) throws Exception
	{
		DOMParser parser = new DOMParser() {

		};
//		parser.parse(new InputSource(FileUtils.getPackageResourceStream("org/sopo/template/Index.html")));
		parser.parse(new InputSource(new StringReader(FileUtils.readPackageResource("web/sopo/template/p1.html"))));
		 print(parser.getDocument(), " ");
//		Document doc = parser.getDocument();
		// ((org.w3c.dom.Element)doc.getDoctype()).getOwnerDocument()
//		System.out.println(nodeToString(doc.getDoctype()));
//		DocumentType docType = doc.getDoctype();
//		NamedNodeMap map = docType.getAttributes();
//		for (int i = 0; null != map && i < map.getLength(); i++)
//		{
//			Node attr = docType.getAttributes().item(i);
//			System.out.println("attr:" + attr.getNodeName() + "::" + attr.getNodeValue());
//
//		}
//		System.out.println(node2String(docType));
//		docType.getAttributes();
		// System.out.println(indent + ((DocumentType)node).compareDocumentPosition(other));
	} // main(String[])

	private static String nodeToString(Node node)
	{
		StringWriter sw = new StringWriter();
		try
		{
			Transformer t = TransformerFactory.newInstance().newTransformer();
//			t.setOutputProperty(OutputKeys., "yes");
//			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		}
		catch (TransformerException te)
		{
			System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
	}
	
	public static String node2String(DocumentType docType)
	{
		String s = "<!DOCTYPE " + docType.getName();

		if (null != docType.getPublicId()) {
			s += " PUBLIC \"" + docType.getPublicId() + "\"";
		} else {
			s += " SYSTEM ";
		}
		
		if (null != docType.getSystemId()) {
			s += " \"" + docType.getSystemId() + "\"";
		}

		s += " >";
		
		return s;
	}

	//
	// Public static methods
	//

	/** Prints a node's class name. */
	public static void print(Node node, String indent)
	{
		NamedNodeMap map = node.getAttributes();
		for (int i = 0; null != map && i < map.getLength(); i++)
		{
			Node attr = node.getAttributes().item(i);
//			 System.out.println(indent + "attr:" + attr.getNodeName() + "::" + attr.getNodeValue());

		}
			
		if (false && node instanceof Comment)
		{
			System.out.println(indent + nodeToString(node));
			System.out.println("--->" + node.getNodeType());
			System.out.println("--->" + Node.COMMENT_NODE);
			System.out.println("--->" + node.getNodeValue());
		}
		
		if (node instanceof Text)
		{
			System.out.println(((Text)node).getNodeValue());
			
		}
		
//		System.out.println(indent + node.getNodeName());
//		System.out.println(indent + node.getClass().getName());

		Node child = node.getFirstChild();
		while (child != null)
		{
			print(child, indent + " ");
			// System.out.println("--->" + child.getTextContent());
			child = child.getNextSibling();
		}
	} // print(Node)

} // class TestHTMLDOM
