package com.kazge.sopoexample.component.grid;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.common.bean.PropertyDescriptor;
import test.common.query.Filter;
import test.common.query.PagingDataSource;
import test.common.query.VirtualQueryResult;
import web.sopo.Request;
import web.sopo.component.Hidden;
import web.sopo.component.Literal;
import web.sopo.component.Table;
import web.sopo.component.Td;
import web.sopo.component.TemplateComponent;
import web.sopo.component.Tr;
import web.sopo.component.Updateable;
import web.sopo.component.Utils;
import web.sopo.component.grid.Grid;
import web.sopo.component.grid.PrepareRowListener;
import web.sopo.util.FileUtils;

public class Grid2 extends TemplateComponent implements Updateable
{
	private Table g2q;
	private GridPageNavigate g2nav;
	private Grid g2grd;
	private Table g2lay;
	private int sortIndex = -1;
	private boolean sortAsc;

	public Grid2()
	{
		g2grd = (Grid) findComponent("g2grd");
		g2nav = (GridPageNavigate) findComponent("g2nav");
		g2q = (Table) findComponent("g2q");
		g2lay = (Table) findComponent("g2lay");
	}

	@Override
	public String getTemplate()
	{
		try
		{
			return FileUtils.readFileToString(new File(Request.getCurrentInstance().getSession().getServletContext()
					.getRealPath("Grid2.html")), "gb2312");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void addPrepareRowListener(PrepareRowListener listener)
	{
		g2grd.addPrepareRowListener(listener);
	}

	public void config(List<Map<String, String>> cols, List<PropertyDescriptor> des)
	{
		final Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
		for (int i = 0; null != des && i < des.size(); i++)
		{
			PropertyDescriptor p = des.get(i);
			map.put(p.getName(), p);
		}
		for (int i = 0; i < cols.size(); i++)
		{
			Map<String, String> attrs = cols.get(i);
			Column2 col = new Column2();
			String bind = attrs.get("bind");
			col.config(attrs, map.get(bind));
			addColumn(col);
		}
	}

	@Override
	public void beforeRender()
	{
		Hidden hid = (Hidden) findComponent("hid");
		hid.setName(getName());
		hid.setValue(sortIndex + ";" + sortAsc);
		Utils.copyAttributes(this, g2lay);
		int colCount = getColumnCount();
		Tr tr = (Tr) g2q.findComponent("g2qtr");
		for (int i = 0; i < colCount; i++)
		{
			Column2 col = getColumn(i);
			Td td = new Td();
			prepareQueryCell(td, col);
			tr.addComponent(td);
		}
	}

	protected void prepareQueryCell(Td td, Column2 column)
	{
		PropertyDescriptor descriptor = column.getDescriptor();

		td.setStyle("width", column.getStyle("width"));
		td.setAttribute("align", "center");
		td.setAttribute("valign", "center");
		QueryCell cell = column.getQueryCell();

		if (!descriptor.isQueryable() || null == cell)
		{
			td.addComponent(new Literal("&nbsp;&nbsp;"));
		}
		else
		{
			td.addComponent(cell);
		}
	}

	@Override
	public void clear()
	{
		g2grd.clear();
	}

	public Column2 getColumn(int index)
	{
		return (Column2) g2grd.getColumn(index);
	}

	public int getColumnCount()
	{
		return g2grd.getColumnCount();
	}

	public void addColumn(Column2 column)
	{
		g2grd.addColumn(column);
	}

	@SuppressWarnings("unchecked")
	public void binding(PagingDataSource dataSource)
	{
		Filter filter = new Filter();
		if (null != Request.getCurrentInstance().getParameter("g2btnAll"))
		{
			//clear query condition will reset the page start index and sort condition
			g2nav.setCurrentIndex(0);
			sortAsc = false;
			sortIndex = -1;
		}
		else
		{
			//collect query conditions every request
			for (int i = 0; i < g2grd.getColumnCount(); i++)
			{
				Column2 col2 = (Column2) g2grd.getColumn(i);
				col2.update(Request.getCurrentInstance());
				filter.getConditions().add(col2.getCondition());

				//when user select one new column to order
				if (col2.isOrder())
				{
					sortAsc = col2.isAsc();
					sortIndex = i;
					g2nav.setCurrentIndex(0);
				}
			}

			if (null != Request.getCurrentInstance().getParameter("g2btnQ"))
			{
				//new query will reset the page start index and sort condition
				g2nav.setCurrentIndex(0);
				sortAsc = false;
				sortIndex = -1;
			}
		}
		if (-1 != sortIndex)
		{
			Column2 sorCol = ((Column2) g2grd.getColumn(sortIndex));
			sorCol.setOrder(true);
			sorCol.setAsc(sortAsc);
			filter.setOrderDispor(sorCol.getDescriptor());
			filter.setAscOrder(sortAsc);
		}
		filter.setStartIndex(g2nav.getCurrentIndex() * g2nav.getPageSize());
		filter.setCount(g2nav.getPageSize());
		VirtualQueryResult result = dataSource.query(filter);
		g2nav.setTotalCount(result.getTotalCount());
		g2grd.binding((List<Object>) result.getResults());
	}

	public void setPageSize(int size)
	{
		g2nav.setPageSize(size);
	}

	public int getPageSize()
	{
		return g2nav.getPageSize();
	}

	public void update(Request request)
	{
		String hv = request.getParameter(getName());
		if (null != hv)
		{
			String[] ss = hv.split(";");
			sortIndex = Integer.parseInt(ss[0]);
			sortAsc = "true".equalsIgnoreCase(ss[1]);
		}
	}

	private String getName()
	{
		return getId() + this.getClass().getName() + "_h";
	}
}
