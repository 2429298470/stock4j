package com.stock4j.exception;

public class UnSupportedException extends Exception{

	private static final long serialVersionUID = 1L;	
	
	public UnSupportedException(String info){
		super(info);
	}
}
