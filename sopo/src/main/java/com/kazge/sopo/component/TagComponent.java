package com.kazge.sopo.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.util.CollectionUtils;
import com.kazge.sopo.util.StringUtils;


public class TagComponent extends Component
{
	private Map<String, String> styles;
	private List<String> csss;

	@Override
	public void render(MarkupWriter writer)
	{		
		writer.writeRaw("<" + getTagName());
		renderAttributes(writer);
		writer.writeRaw(">");
		renderChildren(writer);
		writer.writeRaw("</" + getTagName() + ">");
	}
	
	@Override
	protected void renderAttributes(MarkupWriter writer)
	{
		if (null != styles && !styles.isEmpty())
		{
			Iterator<String> it = styles.keySet().iterator();
			StringBuilder sb = new StringBuilder();
			while (it.hasNext())
			{
				String key = it.next();
				String value = styles.get(key);
				if (null != key && null != value)
				{
					sb.append(key + ":" + value + ";");
				}
			}
			getAttributes().put("style", sb.toString());
		}
		
		if (null != csss && !csss.isEmpty())
		{
			getAttributes().put("class", StringUtils.join(csss," "));
		}
		
		super.renderAttributes(writer);
	}
	
	public void addCss(String cssName)
	{
		getCsss().add(cssName);
	}
	
	public void removeCss(String cssName)
	{
		getCsss().remove(cssName);
	}
	
	public String getStyle(String name)
	{
		return getStyles().get(name);
	}
	
	public void setDisplay(boolean b)
	{
		if (!b)
		{
			setStyle("display", "none");
		}else{
			removeStyle("display");
		}		
	}
	
	public void setEnable(boolean b)
	{
		if (!b)
		{
			setAttribute("disabled", "disabled");			
		}else{
			removeAttribute("disabled");
		}
	}
	
	public boolean isEnable()
	{
		return null == getAttribute("disabled");
	}

	public void setStyle(String name, String value)
	{
		getStyles().put(name, value);
	}
	
	public void removeStyle(String name)
	{
		getStyles().remove(name);
	}

	@Override
	public void setAttribute(String name, String value)
	{		
		if ("style".equalsIgnoreCase(name) && null != value)
		{
			styles = new HashMap<String, String>();
			if (!StringUtils.isBlank(value))
			{
				String [] ss = value.split(";");
				for(int i = 0 ; i < ss.length ; i++)
				{
					String s = ss[i];
					int index = s.indexOf(":");
					if (-1 == index)
					{
						continue;
					}
					String sk = s.substring(0,index);
					String sv = s.substring(index + 1);
					styles.put(sk, sv);
				}
			}
		}else if ("class".equalsIgnoreCase(name) && null != value)
		{
			csss = new ArrayList<String>();
			if (!StringUtils.isBlank(value))
			{
				String [] ss = value.split(" ");
				for(int i = 0 ; i < ss.length ; i++)
				{		
					String s = ss[i];
					if (StringUtils.isBlank(s))
					{
						continue;
					}
					csss.add(value);
				}
			}
		}
		super.setAttribute(name, value);
	}

	private Map<String, String> getStyles()
	{
		if (null == styles)
		{
			styles = new HashMap<String, String>();
		}

		return styles;
	}	

	@SuppressWarnings("unchecked")
	@Override
	public TagComponent clone()
	{
		TagComponent nc = (TagComponent) super.clone();

		if (null != styles)
		{
			nc.styles = CollectionUtils.clone((HashMap) styles);
		}
		
		if (null != csss)
		{
			nc.csss = CollectionUtils.clone((ArrayList) csss);
		}

		return nc;
	}

	private List<String> getCsss()
	{
		if (null == csss)
		{
			csss = new ArrayList<String>();
		}
		
		return csss;
	}
}
