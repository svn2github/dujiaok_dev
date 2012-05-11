package com.ssnn.dujiaok.biz.exception;

@SuppressWarnings("serial")
public class GetHotPlaceException extends RuntimeException{

	public GetHotPlaceException(String message){
		super(message) ;
	}

	public GetHotPlaceException(String message , Throwable e){
		super(message ,e ) ;
	}
}
