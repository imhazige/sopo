package com.kazge.sopo.page;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.MarkupWriterImpl;
import com.kazge.sopo.Request;
import com.kazge.sopo.Response;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.asset.Asset;
import com.kazge.sopo.component.Body;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.component.ComponentRoot;
import com.kazge.sopo.component.CssLink;
import com.kazge.sopo.component.Head;
import com.kazge.sopo.component.Literal;
import com.kazge.sopo.component.ScriptBlock;
import com.kazge.sopo.component.ScriptLink;
import com.kazge.sopo.component.StyleBlock;
import com.kazge.sopo.component.Title;
import com.kazge.sopo.component.Updateable;
import com.kazge.sopo.util.BeanUtils;
import com.kazge.sopo.util.FileUtils;
import com.kazge.sopo.util.Log;
import com.kazge.sopo.util.StringUtils;

public class Page
{
	public Page()
	{
		root = new ComponentRoot();
	}

	public int getMaxPoolCount()
	{
		return 8;
	}

	public static final String PARAM_VIEWSTATE = "__sopo.VIEWSTATE__";

	private Request request;

	private Response response;

	private String title;

	private List<String> cssAssets;
	private List<String> jsAssets;
	private List<String> addedJsId; 
	
	private Map<String, Serializable> viewSate;

	private ComponentRoot root;
	private Head headerElement;
	private Title titleElement;
	private Body body;
	private ScriptBlock scriptBlock;
	private StyleBlock styleBlock;

	public String getTemplate()
	{
		try
		{
			return FileUtils.readFileToString(new File(getSession().getServletContext()
					.getRealPath(this.getClass().getSimpleName() + ".html")));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public boolean isCacheable()
	{
		return true;
	}
	
	public void traversal(Component component)
	{
		includeCssLinks(component.includeCssAssets());
		includeJavascriptLinks(component.includeJavascriptAssets());				
	}

	public void includeCssLinks(Asset[] srcs)
	{
		if (null == srcs)
		{
			return;
		}
		for (int i = 0; i < srcs.length; i++)
		{
			Asset asset = srcs[i];
			if (null == asset)
			{
				continue;
			}
			String src = asset.toUri();
			if (StringUtils.isBlank(src))
			{
				continue;
			}
			if (!getCssAssets().contains(src))
			{
				getCssAssets().add(src);
			}
		}
	}

	public void includeJavascriptLinks(Asset[] srcs)
	{
		if (null == srcs)
		{
			return;
		}
		for (int i = 0; i < srcs.length; i++)
		{
			Asset asset = srcs[i];
			if (null == asset)
			{
				continue;
			}
			String src = asset.toUri();
			if (StringUtils.isEmpty(src))
			{
				continue;
			}
			if (!getJsAssets().contains(src))
			{
				getJsAssets().add(src);
			}
		}
	}

	public void appendScriptBlock(String js)
	{
		if (null == scriptBlock)
		{
			scriptBlock = new ScriptBlock();
		}
		scriptBlock.append(js);
	}

	public void appendScriptBlock(String format, Object... args)
	{
		appendScriptBlock(String.format(format, args));
	}
	
	public void appendOnceScriptBlock(String id,String js)
	{
		if (null == scriptBlock)
		{
			scriptBlock = new ScriptBlock();
		}
		if (null == addedJsId)
		{
			addedJsId = new ArrayList<String>();
		}
		if (addedJsId.contains(id))
		{
			return;
		}
		addedJsId.add(id);
		scriptBlock.append(js);
	}

	public void appendOnceScriptBlock(String id,String format, Object... args)
	{
		appendOnceScriptBlock(id,String.format(format, args));
	}

	@SuppressWarnings("unchecked")
	public void update(Request request)
	{
		String viewStateStr = request.getParameter(PARAM_VIEWSTATE);
		if (!StringUtils.isBlank(viewStateStr))
		{
			viewSate = (HashMap<String, Serializable>) BeanUtils.readObject(viewStateStr);
		}

		update(getRoot(), request);
	}

	@SuppressWarnings("unchecked")
	private void update(Component container, Request request)
	{
		try
		{
			List<Component> children = (List<Component>) BeanUtils.getNestedProperty(container, "children");
			if (null != children)
			{
				Iterator<Component> it = children.iterator();
				while (it.hasNext())
				{
					Component component = it.next();
					update(component, request);
				}	
			}
			if (container instanceof Updateable)
			{
				((Updateable) container).update(request);
			}
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
	}
	
	public void templateReady()
	{
		
	}

	public void onLoad()
	{

	}

	public Request getRequest()
	{
		if (null == request)
		{
			request = Request.getCurrentInstance();	
		}
		
		return request;
	}	

	public Response getResponse()
	{
		return response;
	}

	public void setResponse(Response response)
	{
		this.response = response;
	}

	/**
	 * you should not add any component in this phase,if you do,its resource will not add to the head.  
	 * @date Dec 4, 2009
	 * @author zk
	 * @see
	 */	
	protected void beforeRender()
	{
		
	}

	public void render()
	{
		root.beforeRender();
		if (null != titleElement)
		{
			titleElement.setTitle(title);
		}

		beforeRender();
		
		// resource
		if (null == headerElement)
		{
			Log.warn("no head element is found in the component tree.");
		}
		else
		{
			// css
			for (int i = 0; null != cssAssets && i < cssAssets.size(); i++)
			{
				headerElement.addComponent(new CssLink(cssAssets.get(i)));
				headerElement.addComponent(new Literal("\n"));
			}

			// script
			for (int i = 0; null != jsAssets && i < jsAssets.size(); i++)
			{
				headerElement.addComponent(new ScriptLink(jsAssets.get(i)));
				headerElement.addComponent(new Literal("\n"));
			}
			
			if (null != styleBlock)
			{
				headerElement.addComponent(styleBlock);
				headerElement.addComponent(new Literal("\n"));
			}
			
			if (null != scriptBlock)
			{
				headerElement.addComponent(scriptBlock);
				headerElement.addComponent(new Literal("\n"));
			}
		}		

		MarkupWriter writer = new MarkupWriterImpl(response);
		root.render(writer);
	}

	public HttpSession getSession()
	{
		return getRequest().getSession();
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Map<String, Serializable> getViewSate()
	{
		if (null == viewSate)
		{
			viewSate = new HashMap<String, Serializable>();
		}
		return viewSate;
	}

	public ComponentRoot getRoot()
	{
		return root;
	}

	public List<String> getCssAssets()
	{
		if (null == cssAssets)
		{
			cssAssets = new ArrayList<String>();
		}
		return cssAssets;
	}

	public List<String> getJsAssets()
	{
		if (null == jsAssets)
		{
			jsAssets = new ArrayList<String>();
		}
		return jsAssets;
	}	

	public void setHeaderElement(Head headerElement)
	{
		this.headerElement = headerElement;
	}

	public void setTitleElement(Title titleElement)
	{
		this.titleElement = titleElement;
	}

	public Body getBody()
	{
		return body;
	}

	public void setBody(Body body)
	{
		this.body = body;
	}

	public void componentAdded(Component component)
	{
		if (component instanceof Head)
		{
			this.setHeaderElement((Head)component);
		}
		if (component instanceof Title)
		{
			this.setTitleElement((Title)component);
		}
		if (component instanceof Body)
		{
			this.setBody((Body)component);
		}
		
	}
	
}
