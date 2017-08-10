package com.kazge.sopo.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.kazge.sopo.Constants;
import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.Request;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.asset.Asset;
import com.kazge.sopo.page.Page;


public abstract class Component 
{
	private String tag;
	
	public String getTagName()
	{		
		return tag;
	}
	
	public void setTagName(String tagName)
	{		
		tag = tagName;
	}	
	
	public void beforeRender()
	{

	}

	public abstract void render(MarkupWriter writer);

	private Map<String, String> attributes;

	public Asset [] includeCssAssets()
	{
		return null;
	}
	
	public Asset [] includeJavascriptAssets()
	{
		return null;
	}
	
	public void removeAttribute(String name)
	{
		if (null == attributes)
		{
			return;
		}
		
		attributes.remove(name);
	}

	public void setAttribute(String name, String value)
	{		
		getAttributes().put(name, value);
	}

	public String getAttribute(String name)
	{
		if (null == attributes)
		{
			return null;
		}

		return getAttributes().get(name);
	}	

	protected void renderAttribute(String name, String value, MarkupWriter writer)
	{
		if (Constants.SOPO_TYPE_NS.equalsIgnoreCase(name) || null == value)
		{
			return;
		}
		writer.writef("%s=\"%s\"", name, value);
	}

	protected void renderAttributes(MarkupWriter writer)
	{
		if (null == attributes)
		{
			return;
		}
		Iterator<Map.Entry<String, String>> it = attributes.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, String> entry = it.next(); 
			String name = entry.getKey();
			String value = entry.getValue();
			writer.write(" ");
			renderAttribute(name, value, writer);
		}
	}

	protected void renderChildren(MarkupWriter writer)
	{
		Iterator<Component> it = getChildren().iterator();
		while (it.hasNext())
		{
			Component component = it.next();
			component.render(writer);
		}
	}

	protected Map<String, String> getAttributes()
	{
		if (null == attributes)
		{
			attributes = new HashMap<String, String>();
		}

		return attributes;
	}

	public String getId()
	{
		return getAttributes().get("id");
	}

	public void setId(String id)
	{
		getAttributes().put("id", id);
	}
	
	public String uniqueId(){
		String id = UUID.randomUUID().toString(); 
		
		setId(id);
		
		return id;	
	}

	@Override
	public Component clone()
	{
		try
		{
			Component np = this.getClass().newInstance();
			np.tag = tag;			
			if (null != children)
			{
				np.children = new ArrayList<Component>();
				Iterator<Component> it = getChildren().iterator();
				while (it.hasNext())
				{
					Component comp = it.next().clone();
					np.addComponent(comp);			
				}
			}
			if (null != attributes)
			{
				Utils.copyAttributes(this, np);
			}

			return np;
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
	}
	protected List<Component> children;

	protected Component parent;

	protected List<Component> getChildren()
	{
		if (null == children)
		{
			children = new ArrayList<Component>();
		}

		return children;
	}

	public Page getPage()
	{
		Request request = Request.getCurrentInstance();
		if (null == request){
			return null;
		}
		return request.getPage();
	}

	public void clear()
	{
		if (null == children)
		{
			return;
		}

		children.clear();
	}

	public void addComponent(Component component)
	{
		if (null == component)
		{
			return;
		}
		component.parent = this;
		getChildren().add(component);
		if (null != getPage())
		{
			getPage().componentAdded(component);	
		}		
	}	

	public void removeComponent(Component component)
	{
		getChildren().remove(component);
	}

	public Component findComponent(String id)
	{
		//find in the first-level children
		for (int i = 0; null != getChildren() && i < getChildren().size(); i++)
		{
			Component component = getChildren().get(i);
			if (id.equalsIgnoreCase(component.getId()))
			{
				return component;
			}			
		}
		
		//find in the sub-children
		for (int i = 0; null != getChildren() && i < getChildren().size(); i++)
		{
			Component component = getChildren().get(i);
			
			Component subComponent = component.findComponent(id);
			if (null != subComponent)
			{
				return subComponent;
			}
		}

		return null;
	}
	
	public Component getComponent(int index)
	{
		if (null == children || index >= children.size())
		{
			return null;
		}
		return children.get(index);
	}

	@SuppressWarnings("unchecked")
	public Iterator<Component> iterator()
	{
		final Iterator<Component> it = getChildren().iterator();
		return new Iterator<Component>(){

			public boolean hasNext()
			{
				return it.hasNext();
			}

			public Component next()
			{
				return it.next();
			}

			public void remove()
			{
				throw new SopoException("you should remove the component by use removeComponent.");
			}			
		};
	}

	public Component getParent()
	{
		return parent;
	}
	
}
