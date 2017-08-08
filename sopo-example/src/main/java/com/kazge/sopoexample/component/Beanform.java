package com.kazge.sopoexample.component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.kazge.sopo.Request;
import com.kazge.sopo.asset.Asset;
import com.kazge.sopo.asset.ContextAsset;
import com.kazge.sopo.component.Component;
import com.kazge.sopo.component.Font;
import com.kazge.sopo.component.Label;
import com.kazge.sopo.component.Password;
import com.kazge.sopo.component.Table;
import com.kazge.sopo.component.Td;
import com.kazge.sopo.component.TemplateComponent;
import com.kazge.sopo.component.Text;
import com.kazge.sopo.component.Tr;
import com.kazge.sopo.util.FileUtils;
import com.kazge.sopo.util.StringUtils;
import com.kazge.sopoexample.common.bean.DateDescriptor;
import com.kazge.sopoexample.common.bean.PropertyDescriptor;

public class Beanform extends TemplateComponent
{
	private Table bftb;
	private StringBuilder sb = new StringBuilder();

	public Beanform()
	{
		bftb = (Table) findComponent("bftb");
	}

	@Override
	public Asset[] includeJavascriptAssets()
	{
		return new Asset[] { new ContextAsset("/js/zk/zk.base.js"), new ContextAsset("/js/zk/zk.combobox.js"),
				new ContextAsset("/js/zk/zk.canlendar.js"), new ContextAsset("/js/Beanform.js") };
	}

	@Override
	public Asset[] includeCssAssets()
	{
		return new Asset[] { new ContextAsset("/js/zk/css/base.css"), new ContextAsset("/js/zk/css/combobox.css"),
				new ContextAsset("/js/zk/css/canlendar.css") };
	}

	public void bind(Object model, List<PropertyDescriptor> configs)
	{
		Request request = getPage().getRequest();
		for (int i = 0; i < configs.size(); i++)
		{
			PropertyDescriptor descriptor = configs.get(i);
			if (request.isPost())
			{
				String nv = request.getParameter(getName(descriptor));
				descriptor.modify(model, nv);
			}

			Tr tr = new Tr();

			Td td1 = new Td();
			td1.setStyle("width", "30%");
			Td td2 = new Td();
			td2.setStyle("width", "50%");
			Td td3 = new Td();
			td3.setStyle("width", "20%");

			Label label = new Label(descriptor.getLabel());
			td1.addComponent(label);

			td2.addComponent(resolveComponent(model, descriptor));

			if (descriptor.isRequired())
			{
				Font star = new Font("*");
				star.setStyle("color", "red");
				td3.addComponent(star);
			}

			tr.addComponent(td1);
			tr.addComponent(td2);
			tr.addComponent(td3);

			bftb.addComponent(tr);
		}
	}

	private String getName(PropertyDescriptor descriptor)
	{
		return "bf_" + descriptor.getName();
	}

	private Component resolveComponent(Object model, PropertyDescriptor descriptor)
	{

		switch (descriptor.getUIType())
		{
			case Text:
			{
				Text txt = new Text();
				txt.uniqueId();
				txt.setStyle("width", "100%");
				txt.setName(getName(descriptor));
				txt.setValue(descriptor.display(model));
				if (descriptor.isRequired())
				{
					require(txt.getId(), descriptor.getRequiredTooltip());
				}

				if (!StringUtils.isBlank(descriptor.getRegexp()))
				{
					regex(txt.getId(), descriptor.getRegexp(), descriptor.getRegexpTooltip());
				}

				return txt;
			}

			case Date:
			{
				Calendar calendar = new Calendar();
				calendar.uniqueId();
				calendar.setStyle("width", "100%");
				calendar.setName(getName(descriptor));
				calendar.setEditable(false);
				calendar.setDateFormat(((DateDescriptor) descriptor).getDateformat());
				calendar.setValue(descriptor.display(model));
				if (descriptor.isRequired())
				{
					require(calendar.getId(), descriptor.getRequiredTooltip());
				}

				if (!StringUtils.isBlank(descriptor.getRegexp()))
				{
					regex(calendar.getId(), descriptor.getRegexp(), descriptor.getRegexpTooltip());
				}

				return calendar;
			}

			case Password:
			{
				Password password = new Password();
				password.uniqueId();
				password.setStyle("width", "100%");
				password.setName(getName(descriptor));
				password.setValue(descriptor.display(model));
				if (descriptor.isRequired())
				{
					require(password.getId(), descriptor.getRequiredTooltip());
				}

				if (!StringUtils.isBlank(descriptor.getRegexp()))
				{
					regex(password.getId(), descriptor.getRegexp(), descriptor.getRegexpTooltip());
				}

				return password;
			}
		}

		return null;
	}

	private void require(String id, String msg)
	{
		sb.append(String.format("frm.require(zk.get('%s'),'%s');", id, msg));
	}

	private void regex(String id, String regex, String msg)
	{
		sb.append(String.format("frm.regex(zk.get('%s'),'%s','%s');", id, regex, msg));
	}

	@Override
	public String getTemplate()
	{
		try
		{
			return FileUtils.readFileToString(new File(Request.getCurrentInstance().getSession().getServletContext()
					.getRealPath("Beanform.html")), "gb2312");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void beforeRender()
	{
		getPage().appendOnceScriptBlock(this.getClass().getName(),"zk.ok(function(){var o=zk.get('_form');var frm=new test.Beanform(o);%s});",
									sb.toString());
	}
}
