package org.gatlin.sdk.amazon.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class AmazonException extends SdkException {

	private static final long serialVersionUID = 6183835148214197203L;

	public AmazonException(String code, String desc) {
		super(code, desc);
	}
}
