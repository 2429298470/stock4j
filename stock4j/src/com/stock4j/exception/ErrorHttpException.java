package com.stock4j.exception;

/**
 * ���������쳣
 * @author Administrator
 */
public class ErrorHttpException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ErrorHttpException(){
		super();
	}
	
	public ErrorHttpException(String info){
		super(info);
	}
}
