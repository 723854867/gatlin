package org.gatlin.sdk.jisu.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class JisuException extends SdkException {

	private static final long serialVersionUID = -1439772598594034652L;
	
	public JisuException(String code, String desc) {
		super(code, desc);
	}
}
