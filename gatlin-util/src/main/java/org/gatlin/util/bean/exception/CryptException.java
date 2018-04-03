package org.gatlin.util.bean.exception;

/**
 * 加解密异常
 */
public class CryptException extends RuntimeException {

	private static final long serialVersionUID = 2792800198975288477L;

	public CryptException(String message, Throwable cause) {
		super(message, cause);
	}
}
