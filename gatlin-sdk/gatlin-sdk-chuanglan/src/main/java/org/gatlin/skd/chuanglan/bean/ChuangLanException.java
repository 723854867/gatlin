package org.gatlin.skd.chuanglan.bean;

import org.gatlin.core.bean.exceptions.SdkException;

public class ChuangLanException extends SdkException {

	private static final long serialVersionUID = 609612135333314685L;

	public ChuangLanException(String code, String desc) {
		super(code, desc);
	}
}
