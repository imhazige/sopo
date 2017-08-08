package com.kazge.sopo.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kazge.sopo.Request;
import com.kazge.sopo.component.Component;

public class EventManager
{
	private static final String KEY = "wcsa.event.manager";

	private Map<Component, Map<String, OnListener>> listeners;
	private List<OnListener> lists;

	private EventManager()
	{
	}

	public static EventManager instance()
	{
		Request request = Request.getCurrentInstance();
		if (null == request.getAttribute(KEY))
		{
			request.setAttribute(KEY, new EventManager());
		}

		return (EventManager) request.getAttribute(KEY);
	}

	public void declare(Component component,String eventtype,Object data)
	{		
		OnListener list = initOnListener(component, eventtype);
		list.declare(data);
	}
	
	public void fire()
	{
		for (int i=0 ; null != lists&&i<lists.size(); i++)
		{
			OnListener listener = lists.get(i);
			listener.fire();			
		}	
	}
	
	private OnListener initOnListener(Component component, String eventtype)
	{
		if (null == listeners)
		{
			listeners = new HashMap<Component, Map<String, OnListener>>();
			lists = new ArrayList<OnListener>();
		}
		if (!listeners.containsKey(component))
		{
			listeners.put(component, new HashMap<String, OnListener>());
		}
		Map<String, OnListener> map = listeners.get(component);
		if (!map.containsKey(eventtype))
		{
			map.put(eventtype, new OnListener(component));
		}
		
		return map.get(eventtype);
	}

	public void on(Component component, String eventtype, Listener listener)
	{		
		OnListener list = initOnListener(component, eventtype);
		lists.add(list);
		list.add(listener);
	}
	
	private static final class OnListener
	{
		private boolean on;
		private List<Listener> onlisteners;
		private Component component;
		private Object data;
					
		public OnListener(Component component)
		{
			this.component = component;			
		}
		
		public void add(Listener listener)
		{
			if (null == onlisteners)
			{
				onlisteners = new ArrayList<Listener>();
			}
			onlisteners.add(listener);
		}
		
		public void declare(Object data)
		{
			on = true;
			this.data = data;
		}
		
		public void fire()
		{
			if (!on)
			{
				return;
			}
			for (int i=0 ; null != onlisteners&&i<onlisteners.size(); i++)
			{				
				Listener listener = onlisteners.get(i);
				boolean bl = listener.happen(component,data);
				if (bl)
				{
					return;	
				}	
			}			
		}
	}
}
