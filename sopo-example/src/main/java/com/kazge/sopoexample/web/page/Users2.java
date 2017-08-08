package com.kazge.sopoexample.web.page;

import java.util.List;

import com.kazge.sopoexample.component.grid.Column2;
import com.kazge.sopoexample.component.grid.Grid2;
import com.kazge.sopoexample.web.AppConfig;
import com.kazge.sopoexample.web.remote.RemoteMock;

import test.common.bean.PropertyDescriptor;
import test.common.query.PagingDataSource;
import test.common.query.Filter;
import test.common.query.VirtualQueryResult;
import test.data.User;
import test.service.Command;
import web.sopo.Engine;
import web.sopo.component.Anchor;
import web.sopo.component.grid.Cell;
import web.sopo.component.grid.Grid;
import web.sopo.component.grid.PrepareRowListener;
import web.sopo.component.grid.Grid.Row;
import web.sopo.util.DebugUtils;

public class Users2 extends AbstractPage
{
	private Grid2 grid;

	@Override
	public void onLoad()
	{
		DebugUtils.printRequestParams(getRequest());
		RemoteMock service = RemoteMock.instance();
		grid = (Grid2) getRoot().findComponent("grdUsers");
		boolean postback = getRequest().isPost();

		List<PropertyDescriptor> des = (List<PropertyDescriptor>)service.request(Command.GetUserDescriptors,null);
	
//		grid.setPageSize(6);
		grid.config(((AppConfig)Engine.instance().getAppconfig()).getGrid2Config().get("users2"), des);
		grid.addPrepareRowListener(new RowListener());

		grid.binding(new PagingDataSource(){
			public VirtualQueryResult query(Filter filter)
			{
				return (VirtualQueryResult)RemoteMock.instance().request(Command.GetUsers, filter);
			}			
		});
		
	}

	private static class RowListener implements PrepareRowListener
	{

		public void prepareRow(Row row, int index, Object data)
		{
			if (0 == index % 2)
			{
				row.setStyle("background-color", "olive");
			}
			Grid grid = row.getGrid();
			Cell cell = (Cell) row.getComponent(0);
			cell.clear();
			Anchor anchor = new Anchor();
			String text = ((Column2) grid.getColumn(0)).getDescriptor().display(data);
			anchor.setText(text);
			anchor.setTarget("_blank");
			anchor.setHref(String.format("User.aspx?uid=%s", ((User)data).getUid()));
			cell.addComponent(anchor);
		}
	}
	
}
