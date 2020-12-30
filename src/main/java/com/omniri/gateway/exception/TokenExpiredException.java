package com.omniri.gateway.exception;

public class TokenExpiredException extends RuntimeException {
	  private static final long serialVersionUID = 5861310537366287163L;

	    public TokenExpiredException() {
	        super("Token Already Expired Exception");
	    }

	    public TokenExpiredException(final String message, final Throwable cause) {
	        super(message, cause);
	    }

	    public TokenExpiredException(final String message) {
	        super(message);
	    }

	    public TokenExpiredException(final Throwable cause) {
	        super(cause);
	    }
}