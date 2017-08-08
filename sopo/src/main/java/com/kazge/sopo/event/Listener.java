package com.kazge.sopo.event;

import com.kazge.sopo.component.Component;

public interface Listener
{
	/**
	 * 
	 * @param src
	 * @param data
	 * @return declare if the event stop to bubble: true=stop bubble;false=continue bubble
	 * @date Dec 4, 2009
	 * @author zk
	 * @see
	 */
	boolean happen(Component src,Object data);
}
