package com.kazge.sopoexample.common.query;

import java.io.Serializable;

public class VirtualQueryResult implements Serializable{
	private static final long serialVersionUID = -510679961099454044L;
	private int totalCount;
	private Object results;
	
	public VirtualQueryResult()
	{
	}
	
	public VirtualQueryResult(Object argResults,int count)
	{
		results = argResults;
		totalCount = count;
	}
	
	public int getTotalCount() {
		return this.totalCount;
	}
	public void setTotalCount(int count) {
		this.totalCount = count;
	}
	public Object getResults() {
		return this.results;
	}
	public void setResults(Object results) {
		this.results = results;
	}

}
