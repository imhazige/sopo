package com.kazge.sopo.component.grid;

import java.util.EventListener;

import com.kazge.sopo.component.grid.Grid.Row;

public interface PrepareRowListener extends EventListener 
{
	void prepareRow(Row row,int index,Object data);
}
