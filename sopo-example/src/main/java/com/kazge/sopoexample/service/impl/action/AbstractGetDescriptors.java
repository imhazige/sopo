package com.kazge.sopoexample.service.impl.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.kazge.sopoexample.common.FileUtils;
import com.kazge.sopoexample.common.Log;
import com.kazge.sopoexample.common.StringUtils;
import com.kazge.sopoexample.common.bean.BasePropertyDescriptor;
import com.kazge.sopoexample.common.bean.DateDescriptor;
import com.kazge.sopoexample.common.bean.PropertyDescriptor;
import com.kazge.sopoexample.common.bean.UIType;
import com.kazge.sopoexample.common.convert.BooleanConvertor;
import com.kazge.sopoexample.common.convert.IConvertor;
import com.kazge.sopoexample.common.convert.UITypeConvertor;
import com.kazge.sopoexample.common.xml.IXmlSaxListener;
import com.kazge.sopoexample.common.xml.SAXEventParser;
import com.kazge.sopoexample.service.IAction;
import org.xml.sax.Attributes;

public abstract class AbstractGetDescriptors implements IAction
{
	abstract protected String getXml();
	
	public List<PropertyDescriptor> execute(Object data)
	{
		String path = "com/kazge/sopoexample/service/plxml/" + getXml() + ".xml";
		InputStream in = FileUtils.getPackageResourceStream(path);
		SAXEventParser parser = new SAXEventParser();
		final List<PropertyDescriptor> des = new ArrayList<PropertyDescriptor>();
		parser.addSAXListener("/ps/p", new IXmlSaxListener() {
		
			
			public void content(String text) {
			}

			public void endElement(String uri, String name, String globalName) {
			}

			public void startElement(String uri, String name,
					String globalName, Attributes attributes) {
				BasePropertyDescriptor descriptor = null;
				String clazzType = attributes.getValue("class");
								
				if (StringUtils.isBlank(clazzType))
				{
					descriptor = new BasePropertyDescriptor();
				}else if ("date".equalsIgnoreCase(clazzType)){
					String format = attributes.getValue("dateformat");
					descriptor = new DateDescriptor(format);
				}else{
					throw new RuntimeException("not registered property descriptor type " + clazzType);
				}
				
				IConvertor boolconvert = new BooleanConvertor();
				
				descriptor.setName(attributes.getValue("name"));
				descriptor.setLabel(attributes.getValue("label"));
				descriptor.setVisible((Boolean)boolconvert.convert(attributes.getValue("visible")));
				descriptor.setUIType((UIType)new UITypeConvertor().convert(attributes.getValue("uiType")));
				descriptor.setRequired((Boolean)boolconvert.convert(attributes.getValue("required")));
				descriptor.setRegexp((attributes.getValue("regexp")));
				descriptor.setEditable((Boolean)boolconvert.convert(attributes.getValue("editable")));
				descriptor.setDefaultValue(attributes.getValue("defaultValue"));
				descriptor.setRequiredTooltip(attributes.getValue("requiredTooltip"));
				descriptor.setRegexpTooltip(attributes.getValue("regexpTooltip"));
				descriptor.setQueryable((Boolean)boolconvert.convert(attributes.getValue("queryable")));
				descriptor.setSortable((Boolean)boolconvert.convert(attributes.getValue("sortable")));
				
				des.add(descriptor);
			}

		});
		try {
			parser.parse(in);
			
			return des;			
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.error("load  PropertyDescriptor:" + ex.getMessage());
			throw new RuntimeException(ex);
		}finally{
			if (null != in){
				try {
					in.close();
				} catch (IOException ex) {
				}
			}
		}
		
	}

}
