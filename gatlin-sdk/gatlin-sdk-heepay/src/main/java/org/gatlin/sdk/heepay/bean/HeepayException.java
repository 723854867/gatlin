package org.gatlin.sdk.heepay.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class HeepayException extends SdkException {

	private static final long serialVersionUID = 6183835148214197203L;

	public HeepayException(String code, String desc) {
		super(code, desc);
	}
}
