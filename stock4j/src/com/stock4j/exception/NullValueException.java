package com.stock4j.exception;

public class NullValueException extends Exception{

	private static final long serialVersionUID = 1L;	
	
	public NullValueException(String info){
		super(info);
	}
}
