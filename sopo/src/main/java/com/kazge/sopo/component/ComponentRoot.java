package com.kazge.sopo.component;

import java.util.Iterator;

import com.kazge.sopo.MarkupWriter;

public final class ComponentRoot extends Component
{
	public void beforeRender()
	{
		traversal(this);
	}
	
	private void traversal(Component component)
	{		
		if (null == component.children)
		{
			return;
		}
		Iterator<Component> it = component.children.iterator();
		while(it.hasNext())
		{
			Component subcomp = it.next();			
			subcomp.beforeRender();
			getPage().traversal(subcomp);
			traversal(subcomp);
		}
	}	
	
	@Override
	public void render(MarkupWriter writer)
	{
		renderChildren(writer);		
	}		
}
