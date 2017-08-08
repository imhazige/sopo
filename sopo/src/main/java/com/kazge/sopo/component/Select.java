package com.kazge.sopo.component;

import java.util.ArrayList;
import java.util.List;

import com.kazge.sopo.MarkupWriter;
import com.kazge.sopo.Request;

public class Select extends AbstractFormField
{	
	
	private static final String KEY_TVS = Select.class.getName() + ".TVS";
	
	private int selectIndex = -1;
	
	private List<Option> options;
	
	private String value;
	
	@Override
	public String getTagName()
	{
		return "select";
	}

	public void update(Request request)
	{
		String[] tvs = (String[])getPage().getViewSate().get(KEY_TVS);
		if (null == tvs)
		{
			return;
		}
		clear();
		for(int i = 0 ; i < tvs.length/2 ; i++)
		{
			Option option = new Option(tvs[i*2],tvs[i*2+1]);
			addComponent(option);
		}
		setValue(request.getParameter(getName()));
	}
	
	@Override
	public void addComponent(Component component)
	{
		if (!(component instanceof Option))
		{
			return;
		}
		Option option = (Option)component;
		if (option.isSelected())
		{
			selectIndex = getChildren().size();
			value = option.getValue();
		}	
		super.addComponent(component);
		if (null == options)
		{
			options = new ArrayList<Option>();	
		}
		options.add(option);
	}	
	
	@Override
	public void removeComponent(Component component)
	{
		super.removeComponent(component);
		if (null != options)
		{
			options.remove(component);	
		}		
	}
	
	@Override
	public void clear()
	{
		super.clear();
		if (null != options)
		{
			options.clear();	
		}		
	}

	public int getSelectIndex()
	{
		return selectIndex;
	}

	public void setSelectIndex(int selectIndex)
	{
		if (selectIndex > getChildren().size() - 1)
		{
			throw new IndexOutOfBoundsException("has no such option of index:" + selectIndex);
		}
		if (selectIndex < 0)
		{
			selectIndex = -1;
		}
		if (this.selectIndex == selectIndex)
		{
			return;
		}
		for(int i = 0 ;i < getChildren().size() ; i++)
		{
			Option option = (Option)getChildren().get(i);
			if (selectIndex != i)
			{
				option.setSelected(false);
			}else{
				option.setSelected(true);
			}
		}			
		this.selectIndex = selectIndex;
	}
	
	@Override
	public void setValue(String value)
	{
		selectIndex = -1;
		for(int i = 0 ;i < getChildren().size() ; i++)
		{
			Option option = (Option)getChildren().get(i);
			if (null == value || !value.equalsIgnoreCase(option.getValue()))
			{
				option.setSelected(false);
			}else{
				option.setSelected(true);
				selectIndex = i;
			}
		}
		this.value = value;
	}
	
	@Override
	public String getValue()
	{
		return value;
	}
	
	@Override
	public void beforeRender()
	{
		String[] tvs = new String[getChildren().size() * 2];
		for(int i = 0 ; i < getChildren().size() ; i++)
		{
			Option option = (Option)getChildren().get(i);
			tvs[i*2] = option.getText();
			tvs[i*2+1] = option.getValue();	
		}
		getPage().getViewSate().put(KEY_TVS, tvs);
	}
	
	@Override
	public void render(MarkupWriter writer)
	{			
		super.render(writer);
	}
	
	public List<Option> getOptions()
	{
		return options;
	}
	
	@Override
	public Select clone()
	{
		Select sel = (Select)super.clone();
		
		sel.value = value;
		sel.options = new ArrayList<Option>();
		for(int i = 0 ; i < getChildren().size() ; i++)
		{
			Option option = (Option)getChildren().get(i);
			sel.options.add(option);
		}
		
		return sel;
	}
}
