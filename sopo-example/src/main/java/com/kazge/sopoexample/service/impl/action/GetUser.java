package com.kazge.sopoexample.service.impl.action;

import com.kazge.sopoexample.data.User;
import com.kazge.sopoexample.service.IAction;

public class GetUser implements IAction
{

	public Object execute(Object data)
	{
		String uid = (String)data;

		User user = new User();
		user.setId(Long.valueOf(uid));

		return user;
	}

}
