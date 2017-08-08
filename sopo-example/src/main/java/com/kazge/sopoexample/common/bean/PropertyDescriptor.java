package com.kazge.sopoexample.common.bean;

import java.io.Serializable;

public interface PropertyDescriptor extends Serializable{	
	void modify(Object bean, String value);
	String display(Object model);
	String getName();
	String getLabel();
	boolean isRequired();
	boolean isEditable();
	boolean isVisible();
	String getDefaultValue();
	boolean isQueryable();
	boolean isSortable();
	UIType getUIType();
	String getRequiredTooltip();
	String getRegexpTooltip();
	String getRegexp();
	Object parse(String value);
}
