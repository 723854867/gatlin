package org.gatlin.sdk.alipay.notice;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.model.message.Notice;
import org.gatlin.core.util.Assert;
import org.gatlin.sdk.alipay.SignUtil;

public class AlipayNotice extends Notice {

	private static final long serialVersionUID = 6011629199700226494L;

	@Override
	public void verify() {
		super.verify();
		Assert.isTrue(CoreCode.NOTICE_SIGN_VERIFY_FAILURE, SignUtil.signVerify(this));
	}
}
