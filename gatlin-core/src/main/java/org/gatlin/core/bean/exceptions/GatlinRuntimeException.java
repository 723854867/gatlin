package org.gatlin.core.bean.exceptions;

public class GatlinRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1787730468509203949L;

	public GatlinRuntimeException() {
		super();
	}

	public GatlinRuntimeException(String message) {
		super(message);
	}
	
	public GatlinRuntimeException(Throwable cause) {
		super(cause);
	}

	public GatlinRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
