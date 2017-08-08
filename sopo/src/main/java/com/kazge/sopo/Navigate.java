package com.kazge.sopo;

import com.kazge.sopo.page.Page;

public interface Navigate
{
	Class<? extends Page> mapping(Request request);
}
