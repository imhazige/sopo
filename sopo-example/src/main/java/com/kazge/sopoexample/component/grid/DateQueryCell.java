package com.kazge.sopoexample.component.grid;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.kazge.sopoexample.common.StringUtils;
import com.kazge.sopoexample.common.bean.DateDescriptor;
import com.kazge.sopoexample.common.query.FilterCondition;
import com.kazge.sopoexample.common.query.FilterRelation;
import com.kazge.sopoexample.common.query.FilterType;
import com.kazge.sopoexample.component.Calendar;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.Request;
import com.kazge.sopo.component.TemplateComponent;
import com.kazge.sopo.util.FileUtils;
import com.kazge.sopo.util.Log;

public class DateQueryCell extends QueryCell
{
	private String dp1v;
	private String dp2v;

	@Override
	public FilterCondition resolve(Request request)
	{
		dp1v = request.getParameter(getName1());
		dp2v = request.getParameter(getName2());
		
		FilterCondition fds =  new FilterCondition(FilterType.CONTAINER,FilterRelation.AND,null);
		
		if (!StringUtils.isBlank(dp1v))
		{			
			Date date = null;
			try
			{
				FilterCondition fd = new FilterCondition(FilterType.GREATEREQ,FilterRelation.AND,getDescriptor());
				date = getDescriptor().parse(dp1v);
				fd.setConditionValue(date);
				fds.getSubconditions().add(fd);
			}
			catch (RuntimeException e)
			{
				Log.debug("not valide date:" + dp1v);
			}			
		}
		
		if (!StringUtils.isBlank(dp2v))
		{
			Date date = null;
			try
			{
				FilterCondition fd = new FilterCondition(FilterType.GREATEREQ,FilterRelation.AND,getDescriptor());
				date = getDescriptor().parse(dp2v);
				fd.setConditionValue(date);
				fds.getSubconditions().add(fd);
			}
			catch (RuntimeException e)
			{
				Log.debug("not valide date:" + dp2v);
			}
		}

		return fds;
	}
	
	@Override
	public void beforeRender()
	{
		TemplateComponent comp = new DateQueryComponent();

		Calendar dp1 = (Calendar) comp.findComponent("dp1");
		Calendar dp2 = (Calendar) comp.findComponent("dp2");

		dp1.setName(getName1());
		dp2.setName(getName2());
		
		dp1.setDateFormat(getDescriptor().getDateformat());
		dp2.setDateFormat(getDescriptor().getDateformat());
		
		dp1.setValue(null != dp1v ? dp1v : "");
		dp2.setValue(null != dp2v ? dp2v : "");
		
		addComponent(comp);
	}	
	
	private String getName1()
	{
		return getId() + getDescriptor().getName() + "_dp1";
	}

	private String getName2()
	{
		return getId() + getDescriptor().getName() + "_dp2";
	}

	private static class DateQueryComponent extends TemplateComponent
	{
		@Override
		public String getTemplate()
		{
			try
			{
				return FileUtils.readFileToString(new File(Request.getCurrentInstance().getSession()
						.getServletContext().getRealPath("DateQueryComponent.html")), "gb2312");
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public DateDescriptor getDescriptor()
	{
		return (DateDescriptor)super.getDescriptor();
	}	
}