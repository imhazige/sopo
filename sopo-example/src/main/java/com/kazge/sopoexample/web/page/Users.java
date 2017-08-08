package com.kazge.sopoexample.web.page;

import java.util.List;

import com.kazge.sopoexample.web.remote.RemoteMock;

import test.common.bean.PropertyDescriptor;
import test.common.query.VirtualQueryResult;
import test.data.User;
import test.service.Command;
import web.sopo.component.Anchor;
import web.sopo.component.grid.Cell;
import web.sopo.component.grid.Column;
import web.sopo.component.grid.Grid;
import web.sopo.component.grid.PrepareRowListener;
import web.sopo.component.grid.Grid.Row;

public class Users extends AbstractPage
{
	private Grid grid;

	@Override
	public void onLoad()
	{		
		RemoteMock service = RemoteMock.instance();
		grid = (Grid) getRoot().findComponent("grdUsers");
		boolean postback = getRequest().isPost();

		List<PropertyDescriptor> cols = (List<PropertyDescriptor>)service.request(Command.GetUserDescriptors,null);
		for (int i = 0; i < cols.size(); i++)
		{
			PropertyDescriptor col = cols.get(i);
			if (!col.isVisible())
			{
				continue;
			}
			Column column = new UserColumn(col);
			grid.addColumn(column);
		}
		grid.addPrepareRowListener(new RowListener());

		List<User> uzers = (List<User>)((VirtualQueryResult)service.request(Command.GetUsers,null)).getResults();
		grid.binding(uzers);
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
			String text = ((UserColumn) grid.getColumn(0)).descriptor.display(data);
			anchor.setText(text);
			anchor.setHref(String.format("User.aspx?uid=%s", ((User)data).getUid()));
			cell.addComponent(anchor);
		}
	}

	private static class UserColumn extends Column
	{
		private PropertyDescriptor descriptor;

		public UserColumn(PropertyDescriptor columnDescriptor)
		{
			descriptor = columnDescriptor;
			setName(descriptor.getLabel());
			setBindingName(descriptor.getName());
		}
	}
}
