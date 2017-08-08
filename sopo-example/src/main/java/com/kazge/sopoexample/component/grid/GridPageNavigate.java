package com.kazge.sopoexample.component.grid;

import java.io.File;
import java.io.IOException;

import com.kazge.sopo.Request;
import com.kazge.sopo.component.Button;
import com.kazge.sopo.component.Hidden;
import com.kazge.sopo.component.Label;
import com.kazge.sopo.component.TagComponent;
import com.kazge.sopo.component.TemplateComponent;
import com.kazge.sopo.component.Updateable;
import com.kazge.sopo.util.FileUtils;

public class GridPageNavigate extends TemplateComponent implements Updateable
{
	private int totalCount;

	private int currentIndex;

	private int totalPageCount;
	
	private int pageSize = 20;

	private TagComponent _this;

	private static final String NAME_HID = GridPageNavigate.class.getName() + "_v";

	private static final String NAME_FIRST = GridPageNavigate.class.getName() + "_f";
	private static final String NAME_PREVIOUS = GridPageNavigate.class.getName() + "_p";
	private static final String NAME_NEXT = GridPageNavigate.class.getName() + "_n";
	private static final String NAME_LAST = GridPageNavigate.class.getName() + "_l";

	public GridPageNavigate()
	{
		_this = (TagComponent) findComponent("_this");
	}

	@Override
	public String getTemplate()
	{
		try
		{
			return FileUtils.readFileToString(new File(Request.getCurrentInstance().getSession().getServletContext()
					.getRealPath("GridPageNavigate.html")),"gb2312");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void update(Request request)
	{
		String v = request.getParameter(NAME_HID);
		String[] ss = v.split(";");

		currentIndex = Integer.parseInt(ss[0]);
		totalCount = Integer.parseInt(ss[1]);
		pageSize = Integer.parseInt(ss[2]);
		countPageCount();		
		
		if (null != request.getParameter(NAME_FIRST))
		{
			currentIndex = 0;
		}

		if (null != request.getParameter(NAME_PREVIOUS))
		{
			currentIndex -= 1;
		}

		if (null != request.getParameter(NAME_NEXT))
		{
			currentIndex += 1;
		}

		if (null != request.getParameter(NAME_LAST))
		{
			currentIndex = totalPageCount - 1;
		}
	}

	@Override
	public void beforeRender()
	{
		if (0 == totalCount)
		{
			_this.setEnable(false);
		}
		countPageCount();
		Button aFirst = (Button) findComponent("aFirst");
		aFirst.setName(NAME_FIRST);
		Button aPrevious = (Button) findComponent("aPrevious");
		aPrevious.setName(NAME_PREVIOUS);
		Button aNext = (Button) findComponent("aNext");
		aNext.setName(NAME_NEXT);
		Button aLast = (Button) findComponent("aLast");
		aLast.setName(NAME_LAST);

		Label labTotal = (Label) findComponent("labTotal");
		labTotal.setText(totalPageCount + "");
		Label labCurrent = (Label) findComponent("labCurrent");
		labCurrent.setText(currentIndex + 1 + "");

		Hidden hid = (Hidden) findComponent("hid");
		hid.setName(NAME_HID);

		if (0 == currentIndex)
		{
			aFirst.setEnable(false);
			aPrevious.setEnable(false);
		}

		if (currentIndex == totalPageCount - 1)
		{
			aNext.setEnable(false);
			aLast.setEnable(false);
		}

		hid.setValue(currentIndex + ";" + totalCount + ";" + pageSize);
	}
	
	public int countPageCount()
	{
		totalPageCount = totalCount / pageSize + (0 != totalCount % pageSize?1:0);
		
		return totalPageCount;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public int getCurrentIndex()
	{
		return currentIndex;
	}	

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		if (0 == pageSize){
			throw new RuntimeException("page size can't be zero.");
		}
		this.pageSize = pageSize;
	}

	public void setCurrentIndex(int currentIndex)
	{
		this.currentIndex = currentIndex;
	}
}
