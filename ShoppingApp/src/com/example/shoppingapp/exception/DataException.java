package com.example.shoppingapp.exception;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataException() {
		super();
	}

	public DataException(String message, Throwable cause, boolean enableSuppression, 
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(Throwable cause) {
		super(cause);
	}

}
