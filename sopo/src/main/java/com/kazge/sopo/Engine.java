package com.kazge.sopo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kazge.sopo.asset.AssetHandle;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.event.EventManager;
import com.kazge.sopo.page.Page;
import com.kazge.sopo.page.PageManager;
import com.kazge.sopo.reflection.ClassCache;
import com.kazge.sopo.reflection.ClassNameLocator;
import com.kazge.sopo.util.ClassUtils;
import com.kazge.sopo.util.Log;

public class Engine
{
	private static final String KEY_ENGINE = "sopo.engine";
	private Map<String, String> tagMap = new HashMap<String, String>();

	private AppConfig config;
	private ServletContext context;
	private AssetHandle resourceHandle;


	private Engine()
	{
	}

	public static Engine instance()
	{
		return (Engine) Request.getCurrentInstance().getSession().getServletContext().getAttribute(KEY_ENGINE);
	}

	public boolean run(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		if (null != config.getRequestEncoding())
		{
			req.setCharacterEncoding(config.getRequestEncoding());
		}

		if (null != config.getResponseEncoding())
		{
			resp.setCharacterEncoding(config.getResponseEncoding());
		}
		
		if (resourceHandle.handle(req, resp))
		{
			return true;
		}

		Response response = new Response(resp);
		Request request = new Request(req);
		Page page = PageManager.instance(this).getPage(request, response);
		if (null == page)
		{
			return false;
		}
		page.templateReady();
		if (request.isPost())
		{
			page.update(request);
		}
		page.onLoad();
		EventManager.instance().fire();
		if (!response.isReset())
		{
			page.render();
		}
		request.release();

		return true;
	}

	@SuppressWarnings("unchecked")
	private void _init(ServletContext servletContext)
	{
		context = servletContext;
		String appconfigStr = context.getInitParameter("sopo.config");
		resourceHandle = new AssetHandle();

		Log.info("get config parameter:" + appconfigStr);
		if (null == appconfigStr)
		{
			throw new SopoException("config must be set in the filter's init Parameters.");
		}
		else
		{
			try
			{
				Class<? extends AppConfig> clazz = (Class<? extends AppConfig>) Class.forName(appconfigStr);
				config = clazz.newInstance();
			}
			catch (Exception e)
			{
				throw new SopoException(e);
			}
		}

		config.init(context);
		Map<String, String> map = config.registerLibrary();
		String defaultName = Component.class.getPackage().getName();
		tagMap.put("s", defaultName);
		cacheClassesOfPackage(defaultName, Component.class);
		if (null != map)
		{
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext())
			{
				String key = it.next();
				String value = map.get(key);
				cacheClassesOfPackage(value, Component.class);
				tagMap.put(key.toLowerCase(), value);
			}
		}
	}

	public static Engine init(ServletContext context)
	{
		Engine engine = new Engine();
		engine._init(context);
		context.setAttribute(KEY_ENGINE, engine);

		return engine;
	}

	private void cacheClassesOfPackage(String packageName, Class<?> clazz)
	{
		ClassNameLocator locator = new ClassNameLocator();
		Collection<String> classes = locator.locateClassNames(packageName);
		Iterator<String> it = classes.iterator();
		while (it.hasNext())
		{
			String clazzName = it.next();
			Class<?> itClazz = ClassUtils.forNameOfSubclass(clazzName, clazz);
			if (null != itClazz)
			{
				ClassCache.instance(context).push(clazzName, itClazz);
			}
		}
	}

	public AppConfig getAppconfig()
	{
		return config;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Component> resolveComponent(String tagName)
	{
		int index = tagName.indexOf(":");
		String prefix;
		String className;
		String packagePrefix;

		if (-1 == index)
		{
			prefix = "wcsa";
			className = tagName;
		}
		else
		{
			prefix = tagName.substring(0, index);
			prefix = prefix.toLowerCase();
			className = tagName.substring(index + 1);
		}
		packagePrefix = tagMap.get(prefix);

		if (null == packagePrefix)
		{
			Log.debug("can't find components registered as [%s],current registered tags is [%s]", prefix, tagMap);

			return null;
		}

		if (!"".equals(packagePrefix))
		{
			className = packagePrefix + "." + className;
		}

		Class<? extends Component> clazz = null;
		try
		{
			clazz = (Class<? extends Component>) ClassCache.instance(context).forName(className);
		}
		catch (ClassNotFoundException e)
		{
			Log.error(e);
		}

		if (null == clazz)
		{
			Log.debug("can't find component registered as [%s],current registered tags is [%s]", className, tagMap);
		}

		return clazz;
	}

	public void destroy(ServletContext context)
	{
	}
}
