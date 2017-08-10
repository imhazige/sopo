package com.kazge.sopoexample.service.impl.action;

import com.kazge.sopoexample.data.User;
import com.kazge.sopoexample.service.IAction;

import java.util.ArrayList;
import java.util.List;

public class GetUsers implements IAction
{
	public Object execute(Object data)
	{
		List<User> users = new ArrayList<>();

		users.add(new User("1","1","1","1"));
		users.add(new User("2","2","2","2"));

		return users;
	}

}
