package org.gatlin.sdk.ucpaas;

import org.gatlin.core.bean.exceptions.SdkException;

public class UcpassException extends SdkException {

	private static final long serialVersionUID = 6183835148214197203L;

	public UcpassException(String code, String desc) {
		super(code, desc);
	}
}
