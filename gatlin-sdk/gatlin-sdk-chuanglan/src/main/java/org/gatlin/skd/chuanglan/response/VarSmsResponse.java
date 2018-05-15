package org.gatlin.skd.chuanglan.response;

public class VarSmsResponse extends ChuangLanResponse {

	private static final long serialVersionUID = -2037703879908592938L;

	// 失败条数
	private int failNum;
	private String msgId;
	// 成功条数
	private int successNum;

	public int getFailNum() {
		return failNum;
	}

	public String getMsgId() {
		return msgId;
	}

	public int getSuccessNum() {
		return successNum;
	}
}
