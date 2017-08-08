package com.kazge.sopoexample.common.query;

import java.util.Iterator;
import java.util.List;

import com.kazge.sopoexample.common.bean.PropertyDescriptor;

public class Util
{
	/**
	 * remark this method will remove the elements that be filtered
	 * 
	 * @param it
	 * @param fs
	 */
	public static void filterCollection(Iterator<?> it, List<FilterCondition> fs)
	{
		if (null == fs)
		{
			return;
		}
		while (it.hasNext())
		{
			Iterator<FilterCondition> fit = fs.iterator();
			Object model = it.next();
			while (fit.hasNext())
			{
				FilterCondition fcond = (FilterCondition) fit.next();
				if (null == fcond.getConditionValue())
				{
					continue;
				}
				FilterType ft = fcond.getType();
				PropertyDescriptor dispor = fcond.getDispor();

				// FIXME is there == right?maybe two different object.
				if (ft == FilterType.LEFTLIKE)
				{
					String value = dispor.display(model);
					if (null == value)
					{
						it.remove();
						continue;
					}
					else
					{
						if (0 != value.indexOf((String)fcond.getConditionValue()))
						{
							it.remove();
						}
					}
				}
			}
		}
	}
}
