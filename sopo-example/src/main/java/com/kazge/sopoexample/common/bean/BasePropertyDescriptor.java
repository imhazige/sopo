package com.kazge.sopoexample.common.bean;

import com.kazge.sopoexample.common.BeanUtils;
import com.kazge.sopoexample.common.Log;

public class BasePropertyDescriptor implements PropertyDescriptor
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9025927134819334981L;
	private String name;
	private String label;
	private boolean visible = true;
	private UIType uiType = UIType.Text;
	private boolean required = true;
	private String regexp = null;
	private boolean editable = true;
	private String defaultValue = "";
	private String requiredTooltip = "this is required.";
	private String regexpTooltip = "";
	private boolean queryable;
	private boolean sortable;

	public BasePropertyDescriptor()
	{

	}

	public void modify(Object bean, String value)
	{
		try
		{
			BeanUtils.setNestedProperty(bean, name, parse(value));
		}
		catch (Exception e)
		{
			Log.error(e, "fail to modify the %1s of %2s with value = %3s", name, bean, value);
		}
	}

	public String display(Object bean)
	{
		try
		{
			Object value = BeanUtils.getNestedProperty(bean, name);

			return null != value ? value.toString() : defaultValue;
		}
		catch (Exception e)
		{
			Log.error(e, "fail to display the %1s of %2s.", name, bean);
		}

		return defaultValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public UIType getUIType()
	{
		return uiType;
	}

	public void setUIType(UIType renderType)
	{
		this.uiType = renderType;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public String getRegexp()
	{
		return regexp;
	}

	public void setRegexp(String regexp)
	{
		this.regexp = regexp;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public String getRequiredTooltip()
	{
		return requiredTooltip;
	}

	public void setRequiredTooltip(String requireTooltip)
	{
		this.requiredTooltip = requireTooltip;
	}

	public String getRegexpTooltip()
	{
		return regexpTooltip;
	}

	public void setRegexpTooltip(String regexpTooltip)
	{
		this.regexpTooltip = regexpTooltip;
	}

	public boolean isQueryable()
	{
		return queryable;
	}

	public boolean isSortable()
	{
		return sortable;
	}

	@Override
	public String toString()
	{
		return String
				.format(
						"name=\"%s\" label=\"%s\" visible=\"%s\" uiType=\"%s\" required=\"%s\" regexp=\"%s\" editable=\"%s\" queryable=\"%s\" sortable=\"%s\" defaultValue=\"%s\" requiredTooltip=\"%s\" regexpTooltip=\"%s\" ",
						name, label, visible, uiType, required, regexp, editable,queryable,sortable, defaultValue, requiredTooltip,
						regexpTooltip);
	}

	public void setQueryable(boolean queryable)
	{
		this.queryable = queryable;
	}

	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}

	public Object parse(String value)
	{
		return value;
	}
}
