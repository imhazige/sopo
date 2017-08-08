package com.kazge.sopoexample.data;


public class DataException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6832362110479564872L;

	public DataException() {
		super();
	}

	public DataException(String msg) {
		super(msg);
	}

	public DataException(Throwable ex) {
		super(ex);
	}

}