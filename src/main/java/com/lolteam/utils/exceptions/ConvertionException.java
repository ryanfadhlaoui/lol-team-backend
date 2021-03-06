package com.lolteam.utils.exceptions;


/**
 * This exception may be thrown if a number is badly converted.
 */
public class ConvertionException extends RuntimeException{

	private static final long serialVersionUID = 4174297798164683469L;

	public ConvertionException(String message) {
		super(message);
	}


}
