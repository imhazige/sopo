package com.kazge.sopoexample.web.page;

import java.io.File;
import java.io.IOException;

import com.kazge.sopo.page.Page;
import com.kazge.sopo.util.FileUtils;

public abstract class AbstractPage extends Page
{
	@Override
	public String getTemplate()
	{
		try
		{
			return FileUtils.readFileToString(new File(getSession().getServletContext()
					.getRealPath(this.getClass().getSimpleName() + ".html")),"gb2312");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}	
}
