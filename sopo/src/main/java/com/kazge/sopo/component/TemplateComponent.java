package com.kazge.sopo.component;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.SopoException;
import com.kazge.sopo.template.ComponentTemplate;
import com.kazge.sopo.util.FileUtils;

public abstract class TemplateComponent extends Component
{
	public TemplateComponent()
	{
		new ComponentTemplate(this).parse(getTemplate());
	}	

	public String getTemplate()
	{
		return FileUtils.readPackageResource(this.getClass().getName().replaceAll("\\.", "/") + ".html", this.getClass()
				.getClassLoader());
	}

	@Override
	public void render(MarkupWriter writer)
	{
		renderChildren(writer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TemplateComponent clone()
	{
		try
		{			
			TemplateComponent component = this.getClass().newInstance();
			Utils.copyAttributes(this, component);			

			return component;
		}
		catch (Exception e)
		{
			throw new SopoException(e);
		}
	}
}
