package com.kazge.sopoexample.component;

import web.sopo.asset.Asset;
import web.sopo.asset.ContextAsset;
import web.sopo.component.Text;

public class Calendar extends Text
{

	public void setDateFormat(String format)
	{
		setAttribute("format", format);
	}

	public String getDateFormat()
	{
		return getAttribute("format");
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
	
	@Override
	public void beforeRender()
	{
		if (isEnable())
		{
			getPage().appendOnceScriptBlock(this.getClass().getName(),"zk.ok(function(){window.dp = new zk.Calendar();});");
			setAttribute("onclick", String.format("dp.hide();dp.dateFormatStyle='%s';dp.show({to:this});", getDateFormat()));
		}
	}
}
