package com.kazge.sopo.component.grid;

import com.kazge.sopo.component.Td;
import com.kazge.sopo.component.grid.Grid.Row;

public class Cell extends Td
{
	private Row row; 
			
	public Cell(Row row)
	{
		this.row = row;
	}

	public Row getRow()
	{
		return row;
	}	
}
