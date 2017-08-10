package com.kazge.sopoexample.web.remote;

import com.kazge.sopo.AppConfig;
import com.kazge.sopo.Engine;
import com.kazge.sopoexample.common.Log;
import com.kazge.sopoexample.service.IAction;

public class RemoteMock
{
	public static RemoteMock instance()
	{
		AppConfig config =  Engine.instance().getAppconfig();
		
		RemoteMock mock = (RemoteMock)config.get("mock");
		if (null == mock){
			mock = new RemoteMock();
			config.put("mock", mock);
		}
		
		return mock;
	}
	
	public Object request(Command command,Object data){
		String name = command.name();
		String clazz = String.format("com.kazge.sopoexample.service.impl.action.%s" , name );
		Log.debug( "prepare dispatch action:" + clazz);
		IAction action = null;
		try {
			action = (IAction)Class.forName(clazz).getConstructor((Class [])null).newInstance((Object []) null);
		} catch (Exception ex) {
			Log.error(ex);
			throw new RuntimeException("can't dispach action:" + clazz);
		}
		Object ret = action.execute(data);

		return ret;
	}
}
