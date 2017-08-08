package com.kazge.sopo.component.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kazge.sopo.SopoException;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.component.Label;
import com.kazge.sopo.component.Table;
import com.kazge.sopo.component.Tr;

public class Grid extends Table
{
	public static final String DEFAULT_STYLE = "";
	public static final String DEFAULT_HEADERSTYLE = "color: white;background-color: navy;font-weight: bold"; 
	
	private List<Column> columns = new ArrayList<Column>();

	private Tr header;

	private List<PrepareRowListener> listners = new ArrayList<PrepareRowListener>();

	private boolean newrowCalled = false;
	
	public Grid()
	{
		setAttribute("style", DEFAULT_STYLE);
	}

	public Row newRow()
	{
		if (columns.isEmpty())
		{
			throw new SopoException("none any column in the grid.");
		}
		if (!newrowCalled)
		{
			newrowCalled = true;
		}

		Row row = new Row(this);
		super.addComponent(row);

		return row;
	}

	public void addColumn(Column column)
	{
		if (newrowCalled)
		{
			throw new SopoException("the method addColumn can't be when the newRow method has called.");
		}
		if (null == header)
		{
			header = new Tr();
			header.setAttribute("style", DEFAULT_HEADERSTYLE);
			super.addComponent(header);
		}
		header.addComponent(column);
		columns.add(column);
	}
	
	public void addPrepareRowListener(PrepareRowListener listener)
	{
		listners.add(listener);
	}
	
	public void clearRows()
	{
		Iterator<Component> it = getChildren().iterator();
		while(it.hasNext())
		{
			Component comp = it.next();
			if (comp instanceof Row)
			{
				it.remove();
			}
		}
	}
	
	protected void prepareRow(Row row, int index, Object data)
	{
		Iterator<Component> cit = row.iterator();
		int ci = 0;
		while (cit.hasNext())
		{
			Column column = columns.get(ci);
			Label label = new Label();
			label.setText(column.display(data));
			Cell cell = (Cell)cit.next();
			cell.addComponent(label);
			ci++;
		}
	}

	public void binding(List<?> dataSource)
	{
		Iterator<? extends Object> it = dataSource.iterator();
		int index = 0;
		clearRows();
		while (it.hasNext())
		{
			Row row;
			Object data = it.next();
			row = newRow();
			prepareRow(row, index, data);			
			//fire event
			if (null != listners && !listners.isEmpty())
			{
				Iterator<PrepareRowListener> pit = listners.iterator();
				while (pit.hasNext())
				{
					PrepareRowListener listener = pit.next();
					listener.prepareRow(row, index, data);
				}
			}

			index++;
		}
	}

	@Override
	public void clear()
	{
		super.clear();
		columns.clear();
		newrowCalled = false;
	}

	public static class Row extends Tr
	{
		//no need to overwrite clone,because row will only be added in the application logic flow 
		private Grid grid;

		private Row(Grid grid)
		{
			this.grid = grid;
			for (int i = 0; i < grid.columns.size(); i++)
			{
				Cell cell = new Cell(this);
				getChildren().add(cell);
			}
		}		

		@Override
		public void addComponent(Component component)
		{
			return;
		}

		@Override
		public void removeComponent(Component component)
		{
			return;
		}

		@Override
		public void clear()
		{
			return;
		}
		
		public Grid getGrid()
		{
			return grid;
		}
	}

	public Tr getHeader()
	{
		return header;
	}

	public Column getColumn(int index)
	{
		return columns.get(index);
	}
	
	public int getColumnCount()
	{		
		return null != columns?columns.size():0 ;
	}	
}
