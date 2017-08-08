package com.kazge.sopoexample.web.page;

import java.util.List;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;
import com.kazge.sopoexample.component.Beanform;
import com.kazge.sopoexample.web.remote.RemoteMock;


public class UserPage extends AbstractPage
{
	private Beanform beanform;
	
	@Override
	public void onLoad()
	{
		beanform = (Beanform)getRoot().findComponent("bf");
		RemoteMock service = RemoteMock.instance();
		List<PropertyDescriptor> cols = (List<PropertyDescriptor>)service.request(Command.GetUserDescriptors,null);
		String uid = getRequest().getParameter("uid");
		UserPage.data.User user = (UserPage.data.User)service.request(Command.GetUser, uid);
		beanform.bind(user, cols);
		service.request(Command.UpdateUser,user);
	}
}
