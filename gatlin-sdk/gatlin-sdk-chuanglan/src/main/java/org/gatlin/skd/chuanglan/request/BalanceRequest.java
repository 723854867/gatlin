package org.gatlin.skd.chuanglan.request;

import org.gatlin.skd.chuanglan.response.BalanceResponse;

/**
 * 查询账户余额
 * <pre>
 * 注：客户发送的账号不是253平台登录账号，是短信接口API账号
 * </pre>
 * 
 * @author lynn
 */
public class BalanceRequest extends ChuangLanRequest<BalanceResponse, BalanceRequest> {

	public BalanceRequest() {
		super("ChuangLanConfig.pathBalance()");
		this.body = new Builder();
	}
}
