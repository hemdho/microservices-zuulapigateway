package com.omniri.gateway.exception;

public class TokenInvalidException extends RuntimeException {
	private static final long serialVersionUID = -8133480590920775070L;

	public TokenInvalidException() {
        super("Invalid Token Found Exception");
    }

    public TokenInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TokenInvalidException(final String message) {
        super(message);
    }

    public TokenInvalidException(final Throwable cause) {
        super(cause);
    }

}