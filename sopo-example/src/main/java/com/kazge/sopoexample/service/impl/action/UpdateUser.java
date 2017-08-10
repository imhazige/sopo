package com.kazge.sopoexample.service.impl.action;

import com.kazge.sopoexample.data.User;
import com.kazge.sopoexample.service.IAction;


public class UpdateUser implements IAction
{

	public Object execute(Object data)
	{
		@SuppressWarnings("unused")
		User user = (User)data;
		
		return null;
	}

}
