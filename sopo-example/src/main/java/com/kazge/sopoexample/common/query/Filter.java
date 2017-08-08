package com.kazge.sopoexample.common.query;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;

public class Filter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4523786518655580013L;
	
	private boolean ascOrder;
	private PropertyDescriptor orderDispor;
	private List<FilterCondition> conditions;
	private int startIndex;
	private int count;
	
	public boolean isAscOrder() {
		return this.ascOrder;
	}

	public void setAscOrder(boolean ascOrder) {
		this.ascOrder = ascOrder;
	}
	
	public int getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<FilterCondition> getConditions() {
		if (null == conditions){
			conditions = new Vector<FilterCondition>();
		}
		
		return this.conditions;
	}

	public PropertyDescriptor getOrderDispor() {
		return this.orderDispor;
	}

	public void setOrderDispor(PropertyDescriptor orderDispor) {
		this.orderDispor = orderDispor;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}
}
