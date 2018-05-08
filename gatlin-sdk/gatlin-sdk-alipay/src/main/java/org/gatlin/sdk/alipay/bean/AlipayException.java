package org.gatlin.sdk.alipay.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class AlipayException extends SdkException {

	private static final long serialVersionUID = 6183835148214197203L;

	public AlipayException(String code, String desc) {
		super(code, desc);
	}
}
