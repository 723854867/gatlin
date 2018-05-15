package org.gatlin.sdk.sinapay.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class SinapayException extends SdkException {

	private static final long serialVersionUID = -1619755558775440758L;

	public SinapayException(String code, String desc) {
		super(code, desc);
	}
}
