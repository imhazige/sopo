package com.kazge.sopoexample.web.remote;

import com.kazge.sopo.AppConfig;
import com.kazge.sopo.Engine;

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
		switch (command) {
		case GetUser:
		{
			
			break;
		}
		case GetUserDescriptors:
		{
			break;
		}
		case GetUsers:
		{
			break;
		}
		case UpdateUser:
		{
			break;
		}

		default:
			break;
		}
		return null;
	}
}
