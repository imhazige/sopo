package com.kazge.sopoexample.component.grid;

import java.util.Map;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;
import com.kazge.sopoexample.common.query.FilterCondition;

import com.kazge.sopo.Request;
import com.kazge.sopo.component.AnchorButton;
import com.kazge.sopo.component.Font;
import com.kazge.sopo.component.Hidden;
import com.kazge.sopo.component.Updateable;
import com.kazge.sopo.component.grid.Column;

public class Column2 extends Column implements Updateable
{
	private Map<String, String> configs;

	private PropertyDescriptor descriptor;

	private QueryCell queryCell;

	private FilterCondition condition;

	private boolean order;

	private boolean asc;

	public PropertyDescriptor getDescriptor()
	{
		return descriptor;
	}

	public void setDescriptor(PropertyDescriptor descriptor)
	{
		this.descriptor = descriptor;
	}

	public void update(Request request)
	{
		condition = queryCell.resolve(request);
		if (null != request.getParameter(getSortName()))
		{
			order = true;
			String preAsc = request.getParameter(getHidName());
			if (null == preAsc)
			{
				asc = true;
			}
			else
			{
				asc = !"true".equalsIgnoreCase(preAsc);
			}
		}
		else
		{
			order = false;
		}
	}

	public FilterCondition getCondition()
	{
		return condition;
	}

	public boolean isOrder()
	{
		return order;
	}

	public boolean isAsc()
	{
		return asc;
	}

	public QueryCell getQueryCell()
	{
		return queryCell;
	}

	public void setQueryCell(QueryCell queryCell)
	{
		this.queryCell = queryCell;
	}

	public Map<String, String> getConfigs()
	{
		return configs;
	}

	public void config(Map<String, String> argConfigs, PropertyDescriptor p)
	{
		this.configs = argConfigs;
		String labName = configs.get("name");
		String qcell = configs.get("querycell");
		QueryCell cell = getQueryCell(qcell);
		cell.setDescriptor(p);
		setAttribute("style", argConfigs.get("style"));

		setDescriptor(p);

		setName(null != labName ? labName : (null != p ? p.getLabel() : ""));
		setBindingName(configs.get("bind"));
		setQueryCell(cell);
	}

	private QueryCell getQueryCell(String type)
	{
		if ("text".equalsIgnoreCase(type))
		{
			return new TextQueryCell();
		}
		else if ("date".equalsIgnoreCase(type))
		{
			return new DateQueryCell();
		}
		else if ("number".equalsIgnoreCase(type))
		{
			return new NumberQueryCell();
		}
		else
		{
			return null;
		}
	}

	@Override
	public void beforeRender()
	{
		// sort
		if (null == descriptor || !descriptor.isSortable())
		{
			super.beforeRender();
		}
		else
		{
			AnchorButton button = new AnchorButton();
			button.setStyle("color", "white");
			button.setStyle("font-weight", "bold");
			button.setValue(getName());
			button.setName(getSortName());
			addComponent(button);
			if (isOrder())
			{
				Font font = new Font(isAsc() ? "↑" : "↓");
				font.setStyle("color", "white");
				font.setStyle("font-size", "12px");
				addComponent(font);
				Hidden hid = new Hidden();
				hid.setName(getHidName());
				hid.setValue(isAsc() + "");
				addComponent(hid);
			}
		}
	}

	private String getSortName()
	{
		return getId() + "c_" + descriptor.getName() + "_s";
	}

	private String getHidName()
	{
		return getId() + "c_" + descriptor.getName() + "_h";
	}

	public void setOrder(boolean order)
	{
		this.order = order;
	}

	public void setAsc(boolean asc)
	{
		this.asc = asc;
	}
	
	@Override
	public String display(Object data)
	{
		return getDescriptor().display(data);
	}
}
